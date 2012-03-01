package com.pht.service.auth

import java.util.Map.Entry

import org.apache.commons.codec.digest.DigestUtils

class AuthService {

	/*static*/ List<User> ListUsers() {
		User.list()
	}
	
	/*static*/ User AddUser(Map info) {
		User user = User.newInstance(info)
		user.save(failOnError:true)
	}
	
	/*static*/ User UpdateUser(Map info) {
		User user = GetUser(info.username)
		for (Entry ent in info) {
			user."${ent.key}" = ent.value
		}
		user.save(failOnError:true)
	}

	/*static*/ User GetUser(String username) {
		User.findByUsername(username) ?: fail("No such user: ${username}")
	}

	/*static*/ String DeleteUser(username) {
		GetUser(username).delete();
		"OK"
	}

	/*static*/ Role AddRole(Map info) {
		Role.newInstance(info).save(failOnError:true)
	}

	/*static*/ List<Role> ListRoles() {
		Role.list();
	}

	/*static*/ Role GetRole(String roleName) {
		Role.findByName(roleName) ?: fail("No such role as ${roleName}")
	}

	/*static*/ List<User> ListUsersInRole(String roleName) {
		User.where { roles { name == roleName } }.list()
	}
	
	/*static*/ boolean IsUserInRole(String username_, String roleName) {
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

	/*static*/ String AssignUserRoles(String username, List<String>roleNames) {
		User user = GetUser(username)
		roleNames.each { roleName ->
			user.addToRoles(GetRole(roleName))
		}
		user.save(failOnError:true)
		"OK"
	}

	/*static*/ boolean AuthenticateUser(String username_, String password_) {
//		User user = GetUser(username)
//		user.password == DigestUtils.md5Hex(password)

		// In this case, don't want exception on bad user name, just return false;
		// so don't call GetUser().
		User.whereAny {
			username == username_ && password == DigestUtils.md5Hex(password_)
		}.any()
	}

	private /*static*/ def fail(String message) {
		throw new Exception(message)
	}
}
