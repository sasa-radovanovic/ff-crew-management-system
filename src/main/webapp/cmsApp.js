var cmsApp = angular.module("cmsApp", ['mm.foundation', 'ngCookies','ngRoute',   
                                       ,'ngAnimate', 'ngSanitize']);



cmsApp.config(['$routeProvider', function(routeProvider) {


	routeProvider.when("/", {
		templateUrl: "partials/home.html",
		controller: "homeCtrl",
		resolve: {
		}
	})
	.when("/login", {
		templateUrl: "partials/login.html",
		controller: "loginCtrl",
		resolve: {
		}
	})
	.when("/profile", {
		templateUrl: "partials/profile.html",
		controller: "profileCtrl",
		resolve: {
			profile: function(profileFactory){
				return profileFactory.getProfile();
			}
		}
	})
	.when("/test", {
		templateUrl: "partials/test.html",
		controller: "",
		resolve: {
		}
	})
	.otherwise({redirectTo: '/'});

}]);
