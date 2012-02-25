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

	private String savedPassword
	private String getSavedPassword() {
		throw new Exception("NO")
	}

	def beforeUpdate() {
		println "BEFORE UPDATE"
		if (savedPassword != password) {
			savedPassword = password
			password = DigestUtils.md5Hex(password)
		}
	}

	def beforeInsert() {
		println "BEFORE INSERT"
		savedPassword = password
		password = DigestUtils.md5Hex(password)
		username.replace("#", new Date().toString())
	}

	static constraints = {
		firstName blank: false
		lastName blank: false
		username blank: false, unique: true
		password blank: false
	}

	static sal() {
		new User(
			username:'smendola',
			password:'123',
			lastName: 'Mendola',
			firstName: 'Salvatore'
		)
	}
}
