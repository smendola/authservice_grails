package com.pht.service.logging

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
