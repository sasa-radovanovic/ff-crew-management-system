cmsApp.controller('loginCtrl',
		function($rootScope, $scope, $http, $location) {

	$scope.username = "";
	$scope.password = "";
	
	$scope.login = function () {
		$http.post("/login" , "username=" + $scope.username + "&password=" + $scope.password, {
			headers: {"Content-Type" : "application/x-www-form-urlencoded"}
		}).then(function (data) {
			alert("Logged in success: " + JSON.stringify(data));
			$rootScope.username =  $scope.username;
			$location.path('/profile/');
		}, function (data) {
			alert("Error " + angular.toJson(data));
		});
	};
	

});