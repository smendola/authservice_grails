package com.pht.service.logging

import groovy.transform.ToString

@ToString(includeNames=true, includeFields=true)
class Log {

	String family
	String level
	String message
//	Date loggedAt
	
	def beforeInsert() {
//		loggedAt = new Date()
	}
	
    static constraints = {
		family null: false
		level null: false
		message null:false
    }
}
