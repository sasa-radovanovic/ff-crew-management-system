cmsApp.factory("profileFactory", function($q, $http, $location, $window){
   return {
       getProfile: function(){
    	   var deferred = $q.defer();
    	   $http.post("/user", {}).then(function (data) {
	   			if (data.status == "200") {
	   				if (data == undefined || data.data == undefined || data.data.principal == undefined) {
	   					alert("WHO ARE YOU DUDE?");
	   					$location.path('/login?expired');
	   				}
	   				for (var i = 0; i< data.data.authorities.length; i++) {
	   					if (data.data.authorities[i].authority == 'ADMIN') {
	   						var url = "http://" + $window.location.host + "/backoffice";
	   						$window.location.href = url;
	   						return;
	   					}
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