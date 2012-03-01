package com.pht.service.auth

import org.apache.commons.codec.digest.DigestUtils

import groovy.transform.ToString


@ToString(includeNames=true, includeFields=true)
class User {

	static hasMany = [roles:Role]

	String firstName
	String lastName
	String username
	String password

	def beforeUpdate() {
		if (isDirty('password')) {
			password = DigestUtils.md5Hex(password)
		}
	}

	def beforeInsert() {
		password = DigestUtils.md5Hex(password)
		username.replace("#", new Date().toString())
	}

	static constraints = {
		firstName blank: false
		lastName blank: false
		username blank: false, unique: true
		password blank: false
	}
}
