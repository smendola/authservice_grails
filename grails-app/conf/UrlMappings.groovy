class UrlMappings {

	static mappings = {
		"/auth/$action?" {
			controller = "auth"
		}
		"/logging/$action?" {
			controller = "logging"
		}
//		"/$controller/$action?/$id?"{
//			constraints {
//				// apply constraints here
//			}
//		}

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
