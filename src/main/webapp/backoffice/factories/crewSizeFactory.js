cmsBackoffice.factory("crewSizeFactory", function($q, $http, $location){
   return {
       getCabinCrewSize: function(){
    	   var deferred = $q.defer();
    	   $http.get("/rest/api/crew/size/cabin_crew").then(function (data) {
	   			if (data.status == "200") {
	   				if (data == undefined || data.data == undefined) {
	   					deferred.reject();
	   				}
	   			    deferred.resolve(data.data);
	   			}
	   		}, function (data) {
	   			deferred.reject();
	   		});
    	   return deferred.promise;
       },
       getFlightCrewSize: function(){
    	   var deferred = $q.defer();
    	   $http.get("/rest/api/crew/size/flight_crew").then(function (data) {
	   			if (data.status == "200") {
	   				if (data == undefined || data.data == undefined) {
	   					deferred.reject();
	   				}
	   			    deferred.resolve(data.data);
	   			}
	   		}, function (data) {
	   			deferred.reject();
	   		});
    	   return deferred.promise;
       }
   }
});