cmsBackoffice.controller('homeCtrl',
		function($rootScope, $scope, $http, $location, profile) {

	
	$scope.profile = profile;
	alert(JSON.stringify($scope.profile));
	$scope.user = profile.principal;

	$scope.logout = function () {
		$http.post("/invalidate", {}).then(function (data) {
			if (data.status == "200") {
				$location.path("/");
			}
		}, function (data) {
			alert(angular.toJson(data));
		});
	}
});