cmsBackoffice.controller('flightsCtrl',
		function($rootScope, $scope, $http, $modal, $location, flightPageZero) {


	$scope.currentPage = {
		"activeFlights" : 1
	};
	$scope.flightNum = flightPageZero.totalElements;
	$scope.activeFlightsPage = [];
	if (flightPageZero != undefined && flightPageZero.content != undefined 
			&& flightPageZero.content.length > 0) {
		$scope.activeFlightsPage = flightPageZero.content;
	}
	
	$scope.addNewFlight = function () {
		var modalInstance = $modal.open({
	        templateUrl: 'addNewFlight.html',
	        controller: 'addNewFlightCtrl',
	        resolve: {
	        }
	    });
		
		modalInstance.result.then(function (result) {
			if (result) {
			}
		});
	};
});


cmsBackoffice.controller('addNewFlightCtrl',
		function($scope, $http, $modalInstance) {
	
	
	$scope.newFlight = {
		"flightNumber" : ""	
			
	};
	
	$scope.changeItScheduled = function (flight) {
		alert(JSON.stringify(flight));
		$scope.scheduledFlightSelected = flight;
	};
	
	$scope.closeModal = function () {
		$modalInstance.dismiss('cancel');
	};
	
	$scope.confirm = function () {
		
		$modalInstance.close(true);
			
	};
	
	$scope.retrieveRoutes = function () {
		$scope.scheduledFlightSelected = {};
		$scope.scheduledFlights = [];
		$http.get('/rest/api/scheduled_routes/retrieve_sorted').then(function (data) {
			if (data.data.sortedMap['1'] != undefined) {
				$scope.scheduledFlights = $scope.scheduledFlights.concat(data.data.sortedMap['1']);
			}
			if (data.data.sortedMap['2'] != undefined) {
				$scope.scheduledFlights = $scope.scheduledFlights.concat(data.data.sortedMap['2']);
			}
			if (data.data.sortedMap['3'] != undefined) {
				$scope.scheduledFlights = $scope.scheduledFlights.concat(data.data.sortedMap['3']);
			}
			if (data.data.sortedMap['4'] != undefined) {
				$scope.scheduledFlights = $scope.scheduledFlights.concat(data.data.sortedMap['4']);
			}
			if (data.data.sortedMap['5'] != undefined) {
				$scope.scheduledFlights = $scope.scheduledFlights.concat(data.data.sortedMap['5']);
			}
			if (data.data.sortedMap['6'] != undefined) {
				$scope.scheduledFlights = $scope.scheduledFlights.concat(data.data.sortedMap['6']);
			}
			if (data.data.sortedMap['7'] != undefined) {
				$scope.scheduledFlights = $scope.scheduledFlights.concat(data.data.sortedMap['7']);
			}
			alert("Done " + $scope.scheduledFlights.length);
		});
	};
	$scope.retrieveRoutes();
});