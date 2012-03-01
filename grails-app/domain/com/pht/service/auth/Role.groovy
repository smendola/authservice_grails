package com.pht.service.auth

class Role {
	static hasMany = [users:User]
	static belongsTo = User

	String name;

    static constraints = {
		name blank: false, unique: true, size: 1..200
    }
}
