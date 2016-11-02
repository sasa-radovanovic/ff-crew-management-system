cmsBackoffice.controller('scheduleCtrl',
		function($rootScope, $scope, $http, $location, $modal) {

	$scope.showTip = true;
	$scope.closeTip = function () {
		$scope.showTip = false;
	};
	
	
	$scope.logout = function () {
		$http.post("/invalidate", {}).then(function (data) {
			if (data.status == "200") {
				$location.path("/");
			}
		}, function (data) {
			alert(angular.toJson(data));
		});
	}
	
	$scope.scheduledFlights = {
			'1' : [],
			'2' : [],
			'3' : [],
			'4' : [],
			'5' : [],
			'6' : [],
			'7' : []
	};
	
	$scope.airplaneTypes = [];
	$scope.airplaneTypeSelect = {};
	$scope.selectedAirplaneType = {};
	$scope.retrieveAirplaneTypes = function () {
		$http.get('/rest/api/airplane_types/retrieve_all').then(function (data) {
			$scope.airplaneTypes = data.data;
			$scope.airplaneTypeSelect = data.data[0];
			$scope.selectedAirplaneType = data.data[0];
		});
	};
	$scope.retrieveAirplaneTypes();
	
	
	$scope.changeIt = function (type) {
		$scope.selectedAirplaneType = type;
	};

	
	$scope.retrieveRoutes = function () {
		$scope.scheduledFlights = {
				'1' : [],
				'2' : [],
				'3' : [],
				'4' : [],
				'5' : [],
				'6' : [],
				'7' : []
		};
		$http.get('/rest/api/scheduled_routes/retrieve_sorted').then(function (data) {
			if (data.data.sortedMap['1'] != undefined) {
				$scope.scheduledFlights['1'] = data.data.sortedMap['1'];
			}
			if (data.data.sortedMap['2'] != undefined) {
				$scope.scheduledFlights['2'] = data.data.sortedMap['2'];
			}
			if (data.data.sortedMap['3'] != undefined) {
				$scope.scheduledFlights['3'] = data.data.sortedMap['3'];
			}
			if (data.data.sortedMap['4'] != undefined) {
				$scope.scheduledFlights['4'] = data.data.sortedMap['4'];
			}
			if (data.data.sortedMap['5'] != undefined) {
				$scope.scheduledFlights['5'] = data.data.sortedMap['5'];
			}
			if (data.data.sortedMap['6'] != undefined) {
				$scope.scheduledFlights['6'] = data.data.sortedMap['6'];
			}
			if (data.data.sortedMap['7'] != undefined) {
				$scope.scheduledFlights['7'] = data.data.sortedMap['7'];
			}
		});
	};
	$scope.retrieveRoutes();
	
	$scope.scheduledRouteObject = {
		name : "",
		departure : "",
		arrival : "",
		localDepartureTime: "",
		localArrivalTime: "",
		length : 1
	};
	$scope.daysOfTheWeekObj = {
		monday : false,
		tuesday : false,
		wednesday : false,
		thursday : false,
		friday : false,
		saturday : false,
		sunday : false
	};
	
	
	$scope.addNewScheduledFlight = function () {
		$scope.scheduledRouteObject.airplaneTypeId = $scope.selectedAirplaneType.type;
		$scope.scheduledRouteObject.airplaneTypeManufacturer = $scope.selectedAirplaneType.manufacturer;
		var validationRes = $scope.validateEntries();
		if (validationRes != 'ok') {
			$scope.openErrorModal(validationRes);
			return;
		}
		$scope.scheduledRouteObject.departure = $scope.scheduledRouteObject.departure.toUpperCase();
		$scope.scheduledRouteObject.arrival = $scope.scheduledRouteObject.arrival.toUpperCase();
		$scope.confirmNewScheduledFlight($scope.scheduledRouteObject);
	};
	
	$scope.validateEntries = function () {
		if ($scope.scheduledRouteObject.name.length < 3) {
			return 'name_validation';
		}
		if ($scope.scheduledRouteObject.length <= 0) {
			return 'invalid_length';
		}
		var locDTime = $scope.scheduledRouteObject.localDepartureTime;
		var locATime = $scope.scheduledRouteObject.localArrivalTime;
		if ($scope.validateTimeInput(locDTime) != 0) {
			return 'invalid_departure_time';
		}
		if ($scope.validateTimeInput(locATime) != 0) {
			return 'invalid_arrival_time';
		}
		if ($scope.validateAndFillDays() != 0) {
			return 'invalid_operating_days';
		}
		return 'ok';
	};
	
	$scope.validateTimeInput = function (timeToCheck) {
		if (timeToCheck.length != 5) {
			return -1;
		}
		var timeVals = timeToCheck.split(":");
		if (timeVals.length != 2) {
			return -2;
		}
		if (timeVals[0].length != 2) {
			return -3;
		}
		if (timeVals[1].length != 2) {
			return -4;
		}
		if (isNaN(Number(timeVals[0]))) {
			return -5;
		}
		if (isNaN(Number(timeVals[1]))) {
			return -6;
		}
		if (Number(timeVals[0]) < 0 || Number(timeVals[0]>23)) {
			return -7;
		}
		if (Number(timeVals[1]) < 0 || Number(timeVals[0]>59)) {
			return -8;
		}
		return 0;
	};
	
	$scope.validateAndFillDays = function () {
		$scope.scheduledRouteObject.daysOfTheWeek = [];
		if ($scope.daysOfTheWeekObj['monday']) {
			$scope.scheduledRouteObject.daysOfTheWeek.push(1);
		}
		if ($scope.daysOfTheWeekObj['tuesday']) {
			$scope.scheduledRouteObject.daysOfTheWeek.push(2);
		}
		if ($scope.daysOfTheWeekObj['wednesday']) {
			$scope.scheduledRouteObject.daysOfTheWeek.push(3);
		}
		if ($scope.daysOfTheWeekObj['thursday']) {
			$scope.scheduledRouteObject.daysOfTheWeek.push(4);
		}
		if ($scope.daysOfTheWeekObj['friday']) {
			$scope.scheduledRouteObject.daysOfTheWeek.push(5);
		}
		if ($scope.daysOfTheWeekObj['saturday']) {
			$scope.scheduledRouteObject.daysOfTheWeek.push(6);
		}
		if ($scope.daysOfTheWeekObj['sunday']) {
			$scope.scheduledRouteObject.daysOfTheWeek.push(7);
		}
		if ($scope.scheduledRouteObject.daysOfTheWeek.length == 0) {
			return -1;
		}
		return 0;
	};
	
	$scope.openErrorModal = function (errorEnum) {
	    var modalInstance = $modal.open({
	        templateUrl: 'errorModal.html',
	        controller: 'errorModalCtrl',
	        resolve: {
	          type: function () {
	            return errorEnum;
	          }
	        }
	     });
	};
	
	$scope.deleteScheduledFlight = function (flight_id) {
	    var modalInstance = $modal.open({
	        templateUrl: 'deleteFlightModal.html',
	        controller: 'deleteScheduleFlightModal',
	        resolve: {
	        	del_id: function () {
	            return flight_id;
	          }
	        }
	     });
	    
	    modalInstance.result.then(function (result) {
	    	if(result == 200){
	    		$scope.retrieveRoutes();
	    	} else if (result == 500) {
	    		var modalInstance = $modal.open({
        	        templateUrl: 'errorModal.html',
        	        controller: 'errorModalCtrl',
        	        resolve: {
        	          type: function () {
        	            return 'remove_flight_error';
        	          }
        	        }
        	    });
	    	} else if (result == 401) {
	    		
	    	}
	    });
	};
	
	$scope.confirmNewScheduledFlight = function (flightObj) {
	    var modalInstance = $modal.open({
	        templateUrl: 'newScheduledFlight.html',
	        controller: 'newScheduledFlightModal',
	        resolve: {
	        	flight_object: function () {
	            return flightObj;
	          }
	        }
	     });
	    
	    modalInstance.result.then(function (result) {
            if(result == 201){
            	$scope.scheduledRouteObject = {
            			name : "",
            			departure : "",
            			arrival : "",
            			localDepartureTime: "",
            			localArrivalTime: "",
            			length : 1
            	};
            	$scope.daysOfTheWeekObj = {
            			monday : false,
            			tuesday : false,
            			wednesday : false,
            			thursday : false,
            			friday : false,
            			saturday : false,
            			sunday : false
            	};
            	$scope.retrieveRoutes();
            } else {
            	if (result == 500) {
            		var modalInstance = $modal.open({
            	        templateUrl: 'errorModal.html',
            	        controller: 'errorModalCtrl',
            	        resolve: {
            	          type: function () {
            	            return 'add_new_flight_error';
            	          }
            	        }
            	    });
            	} else if (result == 401) {
            		
            	}
            }
        } );
	};
});


cmsBackoffice.controller('errorModalCtrl',
		function($scope, $modalInstance, type) {
	
	$scope.msg_type = type;
	$scope.msg = $scope.msg_type;
	if (type == 'name_validation') {
		$scope.msg = "You have to enter human-readable name."; 
	} else if (type == 'invalid_length') {
		$scope.msg = "You entered invalid length of the flight. Length must be a number higher than 0."; 
	} else if (type == 'invalid_departure_time') {
		$scope.msg = "You entered invalid value for the DEPARTURE time. Note that departure time must be in " +
				"HH:mm format (i.e. 15:20), and cannot contain letters or any other special symbols. " +
				"Time is scheduled local time at the departure airport."; 
	} else if (type == 'invalid_arrival_time') {
		$scope.msg = "You entered invalid value for the ARRIVAL time. Note that arrival time must be in " +
		"HH:mm format (i.e. 15:20), and cannot contain letters or any other special symbols. " +
		"Time is scheduled local time at the arrival airport."; 
	} else if (type == 'invalid_operating_days') {
		$scope.msg = "You have to select at least one operating day of the week."; 
	}  else if (type == 'add_new_flight_error') {
		$scope.msg = "Internal Server Error occured on adding new flight to the schedule. Please contact system administrator.";
	} else if (type == 'remove_flight_error') {
		$scope.msg = "Internal Server Error occured on removing scheduled flight. Please contact system administrator.";
	} else {
		$scope.msg = "Unrecognized validation error occured [" + $scope.msg_type + "].";
	}
	
	
	
	$scope.closeModal = function () {
		$modalInstance.close('cancel');
	};
	
});


cmsBackoffice.controller('deleteScheduleFlightModal',
		function($scope, $http, $modalInstance, del_id) {
	
	$scope.flightToDeleteId = del_id;
	$scope.msg = "Are you sure you want to delete scheduled flight with id [ " + $scope.flightToDeleteId + " ]?";
	
	
	$scope.closeModal = function () {
		$modalInstance.close('cancel');
	};
	
	$scope.confirmDelete = function () {
		$http.delete('/rest/api/scheduled_routes/remove/' + $scope.flightToDeleteId).then(function (data) {
			$modalInstance.close(data.status);
		});
	};
	
});


cmsBackoffice.controller('newScheduledFlightModal',
		function($scope, $http, $modalInstance, flight_object) {
	
	$scope.flightObject = flight_object;
	$scope.msg = "Are you sure you want to add '" + $scope.flightObject.name +  "'  to the schedule?";
	
	
	$scope.closeModal = function () {
		$modalInstance.dismiss('cancel');
	};
	
	$scope.confirmCreate = function () {
		$http.post('/rest/api/scheduled_routes/create', $scope.flightObject).then(function (data) {
			$modalInstance.close(data.status);
		});
	};
	
});