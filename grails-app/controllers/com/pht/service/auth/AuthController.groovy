package com.pht.service.auth;

import java.lang.reflect.Method

import com.pht.service.JsonRpcController;

import groovy.json.JsonSlurper;

class AuthController extends JsonRpcController {

	AuthService authService; // name is significant; DI keys on it
	
	def index() {
		doJsonRpc(authService);
	}
}