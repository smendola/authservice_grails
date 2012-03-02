package com.pht.service.auth

import groovy.transform.InheritConstructors;

import java.util.Map.Entry

import org.apache.commons.codec.digest.DigestUtils
import org.springframework.transaction.annotation.Transactional

@InheritConstructors
class ServiceException extends Exception {
	int code
	ServiceException(String message, int code) {
		super(message)
		this.code = code
	}
}


class AuthService {

	List<User> ListUsers() {
		User.list()
	}
	
	@Transactional
	User AddUser(HashMap info) {
		User user = User.newInstance(info)
		user.save(failOnError:true)
	}
	
	@Transactional
	User UpdateUser(HashMap info) {
		User user = GetUser(info.username)
		for (Entry ent in info) {
			user[ent.key] = ent.value
		}
		user.save(failOnError:true)
	}

	User GetUser(String username) {
		User.findByUsername(username) ?: fail("No such user: ${username}")
	}

	String DeleteUser(username) {
		User.withTransaction {
			GetUser(username).delete();
		}
		"OK"
	}

	@Transactional
	Role AddRole(HashMap info) {
		Role.newInstance(info).save(failOnError:true)
	}

	List<Role> ListRoles() {
		Role.list();
	}

	Role GetRole(String roleName) {
		Role.findByName(roleName) ?: fail("No such role as ${roleName}")
	}

	List<User> ListUsersInRole(String roleName) {
		User.where { roles { name == roleName } }.list()
	}
	
	@Transactional
	boolean IsUserInRole(String username_, String roleName) {
		// Calling GetRole() and GetUser() ensures appropriate exception
		// is thrown if either object is missing
		GetRole(roleName) in GetUser(username_).roles
//      // This approach is more efficient, but does not raise desired
//      // exception if either username or roleName is invalid		
//  	// variable username_ must be different from column name username
//      User.where {
//          username == username_ && roles.name == roleName
//      }.any()
	}

	@Transactional
	String AssignUserRoles(String username, List<String>roleNames) {
		User user = GetUser(username)
		roleNames.each { roleName ->
			user.addToRoles(GetRole(roleName))
		}
		user.save(failOnError:true)
		"OK"
	}

	boolean AuthenticateUser(String username_, String password_) {
		// In this case, don't want exception on bad user name, just return false;
		// so don't call GetUser().
		User.whereAny {
			username == username_ && password == DigestUtils.md5Hex(password_)
		}.any()
	}

	private def fail(String message, code=100) {
		throw new ServiceException(message, code)
	}
}
