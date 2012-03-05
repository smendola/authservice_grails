package com.pht.service.logging

import org.springframework.transaction.annotation.Transactional

import com.pht.service.logging.dto.LogDto

import grails.gorm.DetachedCriteria

class LoggingService {
	
	LogDto Log(String family, String level, String message) {
		Log log = new Log(family:family, level:level, message:message).save(failOnError:true)
		new LogDto(log)
	}
	
//	protected DetachedCriteria mkQuery(String family=null, String level=null, String expr=null, int max=0, int offset=0) {
//		DetachedCriteria search = Log.where {}
//		if (family) {
//			search = search.where { eq('family', family) }
//		}
//		if (level) {
//			search = search.where { eq('level', level) }
//		}
//		if (max > 0) {
//			search = search.max(max)
//		}
//		if (offset > 0) {
//			search = search.offset(offset)
//		}
//		return search
//	}
	
	protected DetachedCriteria mkQuery(String family=null, String level=null, String expr=null, int max=0, int offset=0) {
		DetachedCriteria search = Log.where {}
			.where { if (family) { eq('family', family) } }
			.where { if (level ) { eq('level',  level ) } }
		if (max > 0) {
			search = search.max(max)
		}
		if (offset > 0) {
			search = search.offset(offset)
		}
		return search
	}

	List<LogDto> Query(String family, String level, String expr, int max, int offset) {
		def query = mkQuery(family, level, expr, max, offset)
		def logs = query.list()
		def res = logs.collect { new LogDto(it) }
	}
	
	@Transactional
	int Purge(String family, String level, int age, String expr) {
		mkQuery(family, level, expr).deleteAll()
	}
	
	Map<String,Integer> Stats(String family, String level, String expr) {
		[count: mkQuery(family, level, expr).count()]
	}
	
}
