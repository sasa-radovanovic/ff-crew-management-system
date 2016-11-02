cmsBackoffice.factory("profileFactory", function($q, $http, $location){
   return {
       getProfile: function(){
    	   var deferred = $q.defer();
    	   $http.post("/user", {}).then(function (data) {
	   			if (data.status == "200") {
	   				if (data == undefined || data.data == undefined || data.data.principal == undefined) {
	   					alert("WHO ARE YOU DUDE?");
	   					$location.path('/login?expired');
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