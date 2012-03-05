import com.pht.service.auth.*
import com.pht.service.logging.LoggingService

class BootStrap {

	def init = { servletContext ->

		AuthService authService = new AuthService()

		authService.AddUser([username:'smendola', password:'123', firstName: 'Sal', lastName:'Mendola'])
		authService.AddUser([username:'jripper', password:'123', firstName: 'Jack', lastName:'Ripper'])
		authService.AddUser([username:'jbauer', password:'123', firstName: 'Jack', lastName:'Bauer'])

        authService.AddRole([name:"Admin"])
        authService.AddRole([name:"Guest"])
        authService.AddRole([name:"Reviewer"])
  		
		authService.AssignUserRoles('smendola', ['Admin', 'Reviewer'])
		
		LoggingService loggingService = new LoggingService()
		
		loggingService.Log("main", "normal", "this is the first log entry")
		loggingService.Log("main", "high", "elevated level")
		loggingService.Log("other", "normal", "this message is in another family")
	}
	def destroy = {
	}
}
