cmsBackoffice.factory("flightPageZeroFactory", function($q, $http, $location){
   return {
       getFlightPageZero: function(){
    	   var deferred = $q.defer();
    	   $http.get("/rest/api/routes/active/page/0").then(function (data) {
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