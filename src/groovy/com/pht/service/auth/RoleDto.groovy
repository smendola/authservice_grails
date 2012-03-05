package com.pht.service.auth

import groovy.transform.Canonical;
import groovy.transform.InheritConstructors;

import com.pht.service.DataTransferObject

@Canonical
@InheritConstructors
class RoleDto extends DataTransferObject {
	String name
}
