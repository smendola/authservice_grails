package com.pht.service
import groovy.transform.InheritConstructors;

@InheritConstructors
class ServiceException extends Exception {
	int code
	ServiceException(String message, int code) {
		super(message)
		this.code = code
	}
}

