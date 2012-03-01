class UrlMappings {

	static mappings = {
		"/auth/$action?" {
			controller = "authService"
		}
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
