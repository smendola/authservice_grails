package com.pht.service.auth

import java.util.Map.Entry

class AuthService {

	def ListUsers() {
		User.list()
	}
	def AddUser(Map info) {
		User user = User.newInstance(info)
		user.save(failOnError:true)
	}
	def UpdateUser(Map info) {
		User user = GetUser(info.username)
		for (Entry ent in info) {
			user."${ent.key}" = ent.value
		}
		user.save(failOnError:true)
	}

	User GetUser(String username /* must be named different from property name! */) {
		User.findByUsername(username) ?: fail("No such user: ${username}")
	}

	def DeleteUser(username) {
		GetUser(username).delete();
	}

	def AddRole(Map info) {
		Role.newInstance(info).save(failOnError:true)
	}

	def ListRoles() {
		Role.list();
	}

	def GetRole(String roleName) {
		Role.findByName(roleName) || fail("No such role as ${roleName}")
	}

	def ListUsersInRole(String roleName) {
	}

	def IsUserInRole(String username, String roleName) {
	}

	def AssignUserRoles(String username, List<String>roleNames) {
	}

	def AuthenticateUser(String username, String password) {
	}

	def fail(String message) {
		throw new Exception(message)
	}
}

/*
 ######################################################################
 ## Implement the Authentication/Authorization Service
 ######################################################################
 class AuthService
 def initialize 
 log = Logger.new STDERR
 log.info  "*************** INIT AuthService ******"
 @user_props = [:username, :first_name, :last_name, :password]
 @role_props = [:name]
 end
 ######################################################################
 # Public API method ListUsers
 ######################################################################
 def ListUsers
 User.all
 end
 ######################################################################
 # Public API method AddUser
 ######################################################################
 def AddUser(info)
 user = User.new
 @user_props.each do |sym|
 val = info[sym.to_s]
 user[sym] = val if not val.nil?
 end
 user.save!
 return user
 end
 ######################################################################
 # Public API method UpdateUser
 ######################################################################
 def UpdateUser(info)
 username = info['username']
 user_id = info['id']
 if not user_id.nil?
 user = User.where(:id => user_id)[0]
 fail("User id #{user_id} does not exist") if user.nil?
 elsif not username.nil?
 user = User.where(:username => username)[0]
 fail("Username #{username} does not exist") if user.nil?
 end
 @user_props.each do |key|
 val = info[key.to_s]
 user[key] = val if not val.nil?
 end
 user.save!
 return user
 end
 ######################################################################
 # Public API method GetUser
 ######################################################################
 def GetUser(username)
 res = User.where(:username => username)
 return res[0] if res.size > 0
 fail("No user by id '#{username}'")
 end
 ######################################################################
 # Public API method DeleteUser
 ######################################################################
 def DeleteUser(username)
 user = User.where(:username => username)[0]
 fail("Username #{username} does not exist") if user.nil?
 user.delete
 end
 ######################################################################
 # Public method Add a Role
 ######################################################################
 def AddRole(info)
 role = Role.new
 @role_props.each do |key|
 val = info[key.to_s]
 role[key] = val if not val.nil?
 end
 role.save!
 return role
 end
 ######################################################################
 # Public method ListRoles
 ######################################################################
 def ListRoles
 Role.all
 end
 def GetRole(role_name)
 roles = Role.where(:name => role_name)
 fail "No role named '#{role_name}'" if roles.empty?
 roles[0]
 end
 ######################################################################
 # Public method ListUsersInRole
 ######################################################################
 def ListUsersInRole(role_name)
 role = GetRole(role_name)
 # TODO: why not this?    Membership.where(:role_id => role.id).map {|m| m.user.username}
 Membership.where(:role_id => role.id).map do |m|
 User.find(m.user_id).username
 end
 end
 ######################################################################
 # Public method IsUserInRole
 ######################################################################
 def IsUserInRole(username, role_name)
 user = GetUser(username)
 role = GetRole(role_name)
 memberships = Membership.where(:role_id => role.id, :user_id => user.id)
 not memberships.empty?
 end
 ######################################################################
 # Public method AssignUserRoles
 ######################################################################
 def AssignUserRoles(username, role_names)
 puts "-> " + username.inspect + " " + role_names.inspect
 u = GetUser(username)
 puts "-> " + u.inspect
 roles = role_names.map { |rn| GetRole(rn) }
 puts "-> " + roles.inspect
 roles.each do |r|
 m = Membership.new :user_id=>u.id, :role_id=>r.id
 puts "-> " + m.inspect
 # TODO: what about "transactionality" if one role assignment fails?
 m.save!
 end
 []
 end
 ######################################################################
 # Public method AuthenticateUser
 ######################################################################
 def AuthenticateUser(username, password)
 user = User.where(:username => username)[0]
 user != nil && user.password == Digest::MD5.hexdigest(password)
 end
 ######################################################################
 # Causes json-rpc request to return an error response
 ######################################################################
 def fail(message)
 raise Exception.new(message)
 end
 end
 */
