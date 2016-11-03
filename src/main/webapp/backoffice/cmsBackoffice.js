var cmsBackoffice = angular.module("cmsBackoffice", ['mm.foundation', 'ngCookies','ngRoute',   
                                       ,'ngAnimate', 'ngSanitize']);



cmsBackoffice.config(['$routeProvider', '$locationProvider',
                      function(routeProvider, locationProvider) {


	routeProvider.when("/home", {
		templateUrl: "/backoffice/partials/home.html",
		controller: "homeCtrl",
		resolve: {
		}
	})
	.when("/panel", {
		templateUrl: "/backoffice/partials/panel.html",
		controller: "panelCtrl",
		resolve: {
		}
	})
	.when("/fleet", {
		templateUrl: "/backoffice/partials/fleet.html",
		controller: "fleetCtrl",
		resolve: {
		}
	})
	.when("/crew", {
		templateUrl: "/backoffice/partials/crew.html",
		controller: "crewCtrl",
		resolve: {
			cabin_size: function (crewSizeFactory) {
				return crewSizeFactory.getCabinCrewSize();
			},
			flight_size: function (crewSizeFactory) {
				return crewSizeFactory.getFlightCrewSize();
			}
		}
	})
	.when("/flights", {
		templateUrl: "/backoffice/partials/flights.html",
		controller: "flightsCtrl",
		resolve: {
			flightPageZero: function (flightPageZeroFactory) {
				return flightPageZeroFactory.getFlightPageZero();
			}
		}
	})
	.when("/schedule", {
		templateUrl: "/backoffice/partials/schedule.html",
		controller: "scheduleCtrl",
		resolve: {
		}
	})
	.otherwise({redirectTo: '/panel'});

}]);


cmsBackoffice.run(['$http', '$location', '$window', '$q', 'profileFactory', '$rootScope', 
         function(http, location, window, q, profileFactory, rootScope) {
	  profileFactory.getProfile().then(function (data) {
		  if (data != undefined && data.authorities != undefined && data.authorities.length > 0 
				  && data.authorities[0].authority == 'ADMIN') {
			  console.log("Hello administrator");
		  } else {
			  var url = "http://" + window.location.host + "/#/profile";
			  window.location.href = url;
			  return;
		  }
	  });
	  
	  rootScope.notImplemented = function () {
	        alert("Not implemented yet!");
	  };
}]);