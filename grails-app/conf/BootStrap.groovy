import org.jabsorb.JSONRPCBridge

import com.pht.service.auth.*

class BootStrap {

	def init = { servletContext ->

		// If the methods are static, then use this form
		//JSONRPCBridge.getGlobalBridge().registerClass("AuthService", AuthService);

		AuthService svc = new AuthService()

		JSONRPCBridge.getGlobalBridge().registerObject("AuthService", svc);
		
		svc.AddUser([username:'smendola', password:'123', firstName: 'Sal', lastName:'Mendola'])
		svc.AddUser([username:'jripper', password:'123', firstName: 'Jack', lastName:'Ripper'])
		svc.AddUser([username:'jbauer', password:'123', firstName: 'Jack', lastName:'Bauer'])

        svc.AddRole([name:"Admin"])
        svc.AddRole([name:"Guest"])
        svc.AddRole([name:"Reviewer"])
  		
		svc.AssignUserRoles('smendola', ['Admin', 'Reviewer'])
	}
	def destroy = {
	}
}
