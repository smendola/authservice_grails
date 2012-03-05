package com.pht.service;

import java.lang.reflect.Method

import groovy.json.JsonSlurper





class JsonRpcController {
	
	class JsonRpcError extends Exception {
		JsonRpcError(msg) {
			super(msg)
		}
	}	
	private fail(msg) {
		throw new JsonRpcError(msg)
	}
	
    protected def doJsonRpc(service) { 
		def resp = [
			version: "1.1",
			error: [
				message: "Now you've done it...",
				code: 997,
				description: "This means there was an internal error in ${getClass().getCanonicalName()}"
			]
		]
		try {
			JsonSlurper js = new JsonSlurper();
			def rpcRequest
			try {
				def rpcStr = request.reader.getText()
				 rpcRequest = js.parseText(rpcStr)
			} catch (groovy.json.JsonException je) {
				fail("Malformed JSON; could not parse")
			}
			String methodName = rpcRequest['method'];			
			if (methodName == null) {
				fail("JSON-RPC method not specified")
			}

			Method m = service.metaClass.methods.find {it.name == methodName}?.getCachedMethod()
			if (m == null) {
				fail("Service does not implement method named " + methodName)
			}
			def params = rpcRequest['params'] ?: [];
		
			def res;
			if (params instanceof List) {
				List<Class> formalTypes = m.parameterTypes;
				if (formalTypes.size() != params.size()) {
					fail("Wrong number of parameters for $methodName; expected ${formalTypes.size()}, got ${params.size()}")
				}
				for (int i=0; i < formalTypes.size(); i++) {
					Class<?> formalType = formalTypes[i]
					Class<?> actualType = params[i].getClass()
					if (params[i] == null  && !formalType.isPrimitive()) continue // allow null for non-primitive parameters
					if (formalType.isPrimitive() && !actualType.isPrimitive()) {
						// Ugh, !isAssignableFrom, but in fact is assignable
						// I tried CachedMethod.getParamType().isValidMethod() and couldn't get it to work
						// so...
						if (formalType == int && actualType == Integer) {
							continue
						}
						// TODO handle other similar combos float/Float, etc.
					}
					if (!formalType.isAssignableFrom(actualType)) {
						def friendly = {cls -> cls.getName().replaceAll(/.*[.]/, '').toLowerCase()}
						fail("Parameter #$i is of the wrong type; expected ${friendly(formalType)}, got ${friendly(actualType)}")
					}
				}
				res = service.invokeMethod(methodName, params as Object[])
			} else {
				fail("json-rpc named parameters not supported")
			}
	
			resp = [ version: "1.1", result: res ]

		} catch (JsonRpcError je) {
			resp = [
				version: "1.1",
				error: [
					message: je.getMessage(),
					code: 999
				]
			]
		} catch (ServiceException se) {
			resp = [
				version: "1.1",
				error: [
					message: se.getMessage(),
					code: se.code
				]
			]

		} catch (Throwable ex) {
			resp = [
				version: "1.1",
				error: [
					message: ex.getMessage(),
					code: 998,
					backtrace: ex.getOurStackTrace()
				]
			]
		} finally {
			render contentType: "application/json", encoding: "UTF-8", { resp }
		}
	}
}
