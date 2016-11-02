cmsApp.controller('profileCtrl',
		function($rootScope, $scope, $http, $location, profile) {

	
	$scope.profile = profile;
	$scope.user = profile.principal;
	
	$scope.username = $rootScope.username;
	
	$scope.activeContent = "upcoming";
	$scope.switchContent = function (content) {
		if ($scope.activeContent == content) {
			return;
		}
		$scope.activeContent = content;
	};
	
	$scope.loading = true;

	$scope.getMe = function () {
		$http.get("/rest/api/crew/retrieve/" + $scope.user.username).then(function (data) {
			$scope.loading = false;
			$scope.crewObject = data.data;
		});	
	};
	$scope.getMe();

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