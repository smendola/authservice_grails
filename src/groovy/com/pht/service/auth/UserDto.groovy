package com.pht.service.auth

import groovy.transform.Canonical;
import groovy.transform.InheritConstructors;

import com.pht.service.DataTransferObject

@Canonical
@InheritConstructors
class UserDto extends DataTransferObject {
	UserDto(User u) {
		super(u)
		first_name = u.firstName
		last_name = u.lastName
	}
	String username
	String first_name
	String last_name
}
