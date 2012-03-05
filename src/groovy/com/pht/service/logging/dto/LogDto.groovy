package com.pht.service.logging.dto

import groovy.transform.Canonical
import groovy.transform.InheritConstructors;

import com.pht.service.DataTransferObject;

@Canonical
@InheritConstructors
class LogDto extends DataTransferObject{
	String family
	String level
	String message
}
