import com.pht.service.auth.*

class BootStrap {

	def init = { servletContext ->

		AuthService svc = new AuthService()

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
