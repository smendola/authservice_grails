package com.pht.service.logging;


import com.pht.service.JsonRpcController;


class LoggingController extends JsonRpcController {

	LoggingService loggingService; // name is significant; DI keys on it
	
	def index() {
		doJsonRpc(loggingService);
	}
}