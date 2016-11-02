cmsBackoffice.controller('fleetCtrl',
		function($rootScope, $scope, $http, $location, $modal) {

	$scope.showTip = true;
	$scope.closeTip = function () {
		$scope.showTip = false;
	};
	
	$scope.showTipAirplaneTypes = true;
	$scope.closeTipAirplaneTypes = function () {
		$scope.showTipAirplaneTypes = false;
	};
	
	$scope.airplaneTypes = [];
	$scope.loadingAirplaneTypes = true;
	$scope.retrieveAirplaneTypes = function () {
		$http.get('/rest/api/airplane_types/retrieve_all').then(function (data) {
			if (data && data.data && data.data.length > 0) {
				$scope.airplaneTypes = data.data;
			}
			$scope.loadingAirplaneTypes = false;
			
		});
		
	};
	$scope.retrieveAirplaneTypes();
	
	$scope.planesInFleet = [];
	$scope.retrieveFleet = function () {
		$scope.loadingFleet = true;
		$http.get('/rest/api/airplane/retrieve_all').then(function (data) {
			if (data && data.data && data.data.length > 0) {
				$scope.planesInFleet = data.data;
				var residue = $scope.planesInFleet.length % 3;
				var emptySlots = (($scope.planesInFleet.length / 3) + 1) * 3 - (($scope.planesInFleet.length / 3) * 3 + residue);
				if (emptySlots > 0) {
					for (var i = 0; i < emptySlots; i++) {
						$scope.planesInFleet.push({
							"id" : "-1"
						});
					}
				}
			}
			$scope.loadingFleet = false;
		});
		
	};
	$scope.retrieveFleet();
	
	$scope.editAirplane = function (plane) {
		var modalInstance = $modal.open({
	        templateUrl: 'editAirplane.html',
	        controller: 'editAirplaneCtrl',
	        resolve: {
	          airplane: function () {
	            return plane;
	          }
	        }
	    });
		
		modalInstance.result.then(function (result) {
			if (result) {
				$scope.retrieveFleet();
			}
		});
	};
	
	$scope.deleteAirplane = function (plane) {
		var modalInstance = $modal.open({
	        templateUrl: 'deleteAirplane.html',
	        controller: 'deleteAirplaneModalCtrl',
	        resolve: {
	          airplane_object: function () {
	            return plane;
	          }
	        }
	    });
		
		modalInstance.result.then(function (result) {
			if (result) {
				$scope.retrieveFleet();
			}
		});
	};
	
	$scope.deleteAirplaneType = function (type) {
		var modalInstance = $modal.open({
	        templateUrl: 'deleteAirplaneType.html',
	        controller: 'deleteAirplaneTypeModalCtrl',
	        resolve: {
	          airplane_type_object: function () {
	            return type;
	          }
	        }
	    });
		
		modalInstance.result.then(function (result) {
			if (result) {
				$scope.retrieveAirplaneTypes();
				$scope.retrieveFleet();
			}
		});
	};
	
	$scope.addNewAirplane = function () {
		var modalInstance = $modal.open({
	        templateUrl: 'addNewAirplane.html',
	        controller: 'addNewAirplaneModalCtrl',
	        resolve: {
	          airplane_types: function () {
	            return $scope.airplaneTypes;
	          }
	        }
	    });
		
		modalInstance.result.then(function (result) {
			if (result) {
				$scope.retrieveFleet();
			}
		});
	};
	
	
	$scope.addNewAirplaneType = function () {
		var modalInstance = $modal.open({
	        templateUrl: 'createAirplaneType.html',
	        controller: 'addAirplaneTypeModalCtrl',
	        resolve: {
	        }
	    });
		
		modalInstance.result.then(function (result) {
			if (result) {
				$scope.retrieveAirplaneTypes();
			}
		});
	};
});


cmsBackoffice.controller('deleteAirplaneModalCtrl',
		function($scope, $http, $modalInstance, airplane_object) {
	
	$scope.airplaneObject = airplane_object;
	$scope.msg = "Are you sure you want to delete airplane '" + $scope.airplaneObject.registration +  "' from the fleet?";
	
	
	$scope.closeModal = function () {
		$modalInstance.dismiss('cancel');
	};
	
	$scope.confirmDelete = function () {
		$http.delete('/rest/api/airplane/' + $scope.airplaneObject.id).then(function (data) {
			if (data && data.status == 200) {
				$modalInstance.close(true);
			} else {
				$modalInstance.close(false);
			}
		});
	};
	
});

cmsBackoffice.controller('deleteAirplaneTypeModalCtrl',
		function($scope, $http, $modalInstance, airplane_type_object) {
	
	$scope.airplaneTypeObject = airplane_type_object;
	$scope.msg = "Are you sure you want to delete type '" + $scope.airplaneTypeObject.manufacturer + " " + $scope.airplaneTypeObject.type +   "' from the database?" +
			" NOTE: This operation will remove all airplanes in the fleet on this type, but already created flights with these airplanes/airplane types will remain.";
	
	
	$scope.closeModal = function () {
		$modalInstance.dismiss('cancel');
	};
	
	$scope.confirmDelete = function () {
		$http.delete('/rest/api/airplane_type/' + $scope.airplaneTypeObject.id).then(function (data) {
			if (data && data.status == 200) {
				$modalInstance.close(true);
			} else {
				$modalInstance.close(false);
			}
		});
	};
	
});


cmsBackoffice.controller('addAirplaneTypeModalCtrl',
		function($scope, $http, $modalInstance) {
	
	$scope.newAirplaneTypeObject = {
		"manufacturer" : "",
		"type" : ""
	};

	
	$scope.closeModal = function () {
		$modalInstance.dismiss('cancel');
	};
	
	$scope.validationError = false;
	$scope.confirmDelete = function () {
		if ($scope.newAirplaneTypeObject.manufacturer.length < 3 || $scope.newAirplaneTypeObject.type.length <  3) {
			$scope.validationError = true;
		}
		$http.post('/rest/api/airplane_types/create', $scope.newAirplaneTypeObject).then(function (data) {
			if (data && data.status == 201) {
				$modalInstance.close(true);
			} else {
				$modalInstance.close(false);
			}
		});
	};
	
});


cmsBackoffice.controller('addNewAirplaneModalCtrl',
		function($scope, $http, $modalInstance, airplane_types) {
	
	$scope.airplaneTypes = airplane_types;
	
	$scope.newAirplaneObject = {
		"registration" : "",
		"seatCapacity" : 1,
		"manufacturer" : "",
		"type" : "",
		"deliveryDate": "",
		"msnNumber" : ""
	};
		
	$scope.closeModal = function () {
		$modalInstance.dismiss('cancel');
	};
	
	$scope.changeIt = function (type) {
		$scope.selectedAirplaneType = type;
	};
	
	$scope.validationError = 0;
	
	$scope.confirm = function () {
		var validation = $scope.validate();
		if (validation != 0) {
			$scope.validationError = validation;
			return;
		}
		$http.post('/rest/api/airplane/create', $scope.newAirplaneObject).then(function (data) {
			if (data && data.status == 201) {
				$modalInstance.close(true);
			} else {
				$scope.validationError = -100;
			}
		});
	};
	
	$scope.validate = function () {
		if ($scope.selectedAirplaneType == undefined) {
			return -1;
		} else {
			$scope.newAirplaneObject.manufacturer = $scope.selectedAirplaneType.manufacturer;
			$scope.newAirplaneObject.type = $scope.selectedAirplaneType.type;
		}
		if ($scope.newAirplaneObject.msnNumber.length < 3) {
			return -3;
		} 
		if ($scope.newAirplaneObject.registration.length < 4) {
			return -4;
		}
		if (!$scope.validateDeliveryDate($scope.newAirplaneObject.deliveryDate)) {
			return -5;
		}
		return 0;
	};
	
	$scope.validateDeliveryDate = function (date_string) {
		if (date_string.length != 10) {
			return false;
		}
		var timeVals = date_string.split("/");
		if (timeVals.length != 3) {
			return false;
		}
		if (timeVals[0].length != 2 || timeVals[1].length != 2 || timeVals[2].length != 4) {
			return false;
		}
		if (isNaN(Number(timeVals[0])) || isNaN(Number(timeVals[1])) || isNaN(Number(timeVals[2]))) {
			return false;
		}
		if (Number(timeVals[0]) < 0 || Number(timeVals[0]>31)) {
			return false;
		}
		if (Number(timeVals[1]) < 0 || Number(timeVals[1]>12)) {
			return false;
		}
		if (Number(timeVals[2]) < 1950 || Number(timeVals[2]>2016)) {
			return false;
		}
		return true;
	};
	
	$scope.generateErrorText = function (error_code) {
		if (error_code == -1) {
			return 'Please select airplane type.';
		} else if (error_code == -3) {
			return 'MSN number is missing.';
		} else if (error_code == -4) {
			return 'Registration is missing.';
		} else if (error_code == -5) {
			return 'Delivery date must be in dd/MM/YYYY format.';
		} else if (error_code == -100) {
			return 'Internal Server Error occured. Please contact system administrator.';
		} else {
			'Error';
		}
	};
	
});



cmsBackoffice.controller('editAirplaneCtrl',
		function($scope, $http, $modalInstance, airplane) {
	
	$scope.airplane = airplane;
	$scope.airplaneType = airplane.airplaneType.manufacturer + " " + airplane.airplaneType.type;
	
	$scope.closeModal = function () {
		$modalInstance.dismiss('cancel');
	};
	
	$scope.validationError = 0;
	
	$scope.confirm = function () {
		if ($scope.airplane.registration == airplane.registration 
				&& $scope.airplane.seatCapacity == airplane.seatCapacity) {
			$modalInstance.close(false);
		}
		if ($scope.airplane.registration.length < 3) {
			$scope.validationError = 1;
			return;
		}
		$http.post('/rest/api/airplane/update', {
			"id" : $scope.airplane.id,
			"registration" : $scope.airplane.registration,
			"seatCapacity" : $scope.airplane.seatCapacity
		}).then(function (data) {
			if (data && data.status == 200) {
				$modalInstance.close(true);
			} else {
				$scope.validationError = -100;
			}
		});
	};

	
});