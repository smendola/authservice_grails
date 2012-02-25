package com.pht.service.auth

import java.util.Date;

class Role extends DomainObject {
	static hasMany = [roles:Role]
	String name;

    static constraints = {
		name blank: false, unique: true, size: 1..200
    }
}
