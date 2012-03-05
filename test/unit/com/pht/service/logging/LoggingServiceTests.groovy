package com.pht.service.logging



import grails.test.mixin.*
import org.junit.*

import com.pht.service.auth.AuthService

/**
 * See the API for {@link grails.test.mixin.domain.serviceUnitTestMixin} for usage instructions
 */
@TestFor(LoggingService)
@Mock([Log]) // won't work w/o mocking; why?
class LoggingServiceTests {
	
	// "service" is injected and *cannot* be declared in this file;
	// however that is invisible to the IDE; we want a declaration 
	// so IDE knows about this member, and its type.
	// Solution is to create this alias like this:
	LoggingService svc = service
	
	@Test
	void addLog() {
		def a = svc.Log("main", "dflt", "this is a  message")
		def b = svc.Log("main", "high", "this is a  message")
		assert Log.count() == 2
	}
	
	@Test
	void queryNoMatch() {
		def a = svc.Log("main", "dflt", "this is a  message")
		def b = svc.Log("main", "high", "this is a  message")
		def res = svc.Query("garbage", "garbage", null, 0, 0)
		assert res.size() == 0
	}
	
	@Test
	void queryFamilyMatch() {
		def a = svc.Log("main", "dflt", "this is a  message")
		def b = svc.Log("main", "high", "this is a  message")
		def c = svc.Log("other", "high", "this is a  message")
		def res = svc.Query("main", null, null, 0, 0)
		assert res.size() == 2
		def res2 = svc.Query("other", null, null, 0, 0)
		assert res2.size() == 1
	}
	
	@Test
	void queryLevelMatch() {
		def a = svc.Log("main", "dflt", "this is a  message")
		def b = svc.Log("main", "high", "this is a  message")
		def c = svc.Log("other", "high", "this is a  message")
		def res = svc.Query(null, "dflt", null, 0, 0)
		assert res.size() == 1
		def res2 = svc.Query(null, "high", null, 0, 0)
		assert res2.size() == 2
	}

	@Test
	void queryFamilyAndLevelMatch() {
		def a = svc.Log("main", "dflt", "this is a  message")
		def b = svc.Log("main", "high", "this is a  message")
		def c = svc.Log("other", "high", "this is a  message")
		def res = svc.Query("main", "high", null, 0, 0)
		assert res.size() == 1
	}
	
	@Test
	void purge() {
		def a = svc.Log("main", "dflt", "this is a  message")
		def b = svc.Log("main", "high", "this is a  message")
		def c = svc.Log("other", "high", "this is a  message")
		def res = svc.Purge("main", null, 0, null)
		assert Log.count() == 1
	}
	
	@Test
	void stats() {
		def a = svc.Log("main", "dflt", "this is a  message")
		def b = svc.Log("main", "high", "this is a  message")
		def c = svc.Log("other", "high", "this is a  message")
		def res = svc.Stats(null, "high", null);
		assert res.count == 2
	}

}
