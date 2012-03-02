package com.pht.service.auth;

import java.lang.reflect.Method

import groovy.json.JsonSlurper;

class AuthController {

	class JsonRpcError extends Exception {
		JsonRpcError(msg) {
			super(msg)
		}
	}
	AuthService authService; // name is significant; DI keys on it
	
	private fail(msg) {
		throw new JsonRpcError(msg)
	}
	
    def index() { 
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

			Method m = authService.metaClass.methods.find {it.name == methodName}.getCachedMethod()
			if (m == null) {
				fail("Service does not implement method named " + methodName)
			}
			def params = rpcRequest['params'] ?: [];
		
			def res;
			if (params instanceof List) {
				List<Class> formals = m.getParameterTypes();
				if (formals.size() != params.size()) {
					fail("Wrong number of parameters for $methodName; expected ${formals.size()}, got ${params.size()}")
				}
				for (int i=0; i < formals.size(); i++) {
					if (!formals[i].isAssignableFrom(params[i].getClass())) {
						fail("Parameter #$i is of the wrong type; expected ${formals[i].getClass().getName()}, got ${params[i].getClass().getName()}")
					}
				}
				res = authService.invokeMethod(methodName, params as Object[])
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
