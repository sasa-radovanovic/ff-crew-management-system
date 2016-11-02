cmsBackoffice.controller('crewCtrl',
		function($rootScope, $scope, $http, $location, $modal, cabin_size, flight_size) {

	if (cabin_size != undefined && cabin_size >= 0) {
		$scope.cabinCrewNum = cabin_size;
		$scope.cabinCrewNumUnt = cabin_size;
	} else {
		$scope.cabinCrewNum = 0;
		$scope.cabinCrewNumUnt = 0;
	}
	
	if (flight_size != undefined && flight_size >= 0) {
		$scope.flightCrewNum = flight_size;
		$scope.flightCrewNumUnt = flight_size;
	} else {
		$scope.flightCrewNum = 0;
		$scope.flightCrewNumUnt = 0;
	}
	
	$scope.hardReloadCabinPage = function () {
		$http.get("/rest/api/crew/size/cabin_crew").then(function (data) {
   			if (data.status == "200") {
   				$scope.cabinCrewNum = data.data;
   				$scope.cabinCrewNumUnt = data.data;
   				$scope.retrieveCabinCrewPage($scope.currentPage.cabin);
   			}
   		}, function (data) {
   			$scope.cabinCrewNum = 0;
   		});
	};
	
	$scope.hardReloadFlightPage = function () {
		$http.get("/rest/api/crew/size/flight_crew").then(function (data) {
   			if (data.status == "200") {
   				$scope.flightCrewNum = data.data;
   				$scope.flightCrewNumUnt = data.data;
   				$scope.retrieveFlightCrewPage($scope.currentPage.flight);
   			}
   		}, function (data) {
   			$scope.cabinCrewNum = 0;
   		});
	};

	$scope.activePage = 'cabin';
	$scope.advancedSearchDone = false;


	$scope.currentPage = {
		"cabin" : 1,
		"flight" : 1
	};

	$scope.loadedCabinCrew = [];

	$scope.loadingCabinCrew = false;

	$scope.retrieveCabinCrewPage = function (page) {
		$scope.loadingCabinCrew = true;
		$http.get('/rest/api/cabin_crew/page/' + (page - 1)).then(function(data) {
			$scope.loadingCabinCrew = false;
			$scope.loadedCabinCrew = data.data.content;
		});
	};

	$scope.$watch('currentPage.cabin', function (newValue, oldValue) {
		if ($scope.activePage == 'cabin' && newValue != -1) {
			$scope.retrieveCabinCrewPage(newValue);
		}
	});


	$scope.currentPageFlightCrew = 1;

	$scope.loadedFlightCrew = [];

	$scope.loadingFlightCrew = false;

	$scope.retrieveFlightCrewPage = function (page) {
		$scope.loadingFlightCrew = true;
		$http.get('/rest/api/flight_crew/page/' + (page - 1)).then(function(data) {
			$scope.loadingFlightCrew = false;
			$scope.loadedFlightCrew = data.data.content;
		});
	};

	$scope.$watch('currentPage.flight', function (oldValue, newValue) {
		if ($scope.activePage == 'flight' && newValue != -1) {
			$scope.retrieveFlightCrewPage(newValue);
		}
	});
	
	$scope.changeContent = function (content) {
		if ($scope.advancedSearchDone) {
			$scope.advancedSearchDone = false;
		}
		$scope.activePage = content;
		if (content == 'cabin' ) {
			$scope.retrieveCabinCrewPage($scope.currentPage.cabin);
		} else {
			$scope.retrieveFlightCrewPage($scope.currentPage.flight);
		}
	};

	$scope.addNewCrew = function (type) {
		var modalInstance = $modal.open({
	        templateUrl: 'addNewCrew.html',
	        controller: 'addNewCrewCtrl',
	        resolve: {
	          type: function () {
	            return type;
	          }
	        }
	    });
		
		modalInstance.result.then(function (result) {
			if (result) {
				if ($scope.activePage == 'cabin') {
					$scope.hardReloadCabinPage();
				} else if ($scope.activePage == 'flight') {
					$scope.hardReloadFlightPage();
				}
			}
		});
	};
	
	
	$scope.editCrew = function (crew) {
		var modalInstance = $modal.open({
	        templateUrl: 'editCrew.html',
	        controller: 'editCrewCtrl',
	        resolve: {
	          crew_object: function () {
	            return crew;
	          }
	        }
	    });
		
		modalInstance.result.then(function (resultCrew) {
			if (resultCrew instanceof Object) {
				if ($scope.activePage == 'cabin') {
					for (var i = 0; i < $scope.loadedCabinCrew.length; i ++) {
						if ($scope.loadedCabinCrew[i].id == resultCrew.id) {
							$scope.loadedCabinCrew[i] = resultCrew;
						}
					}
				} else {
					for (var i = 0; i < $scope.loadedFlightCrew.length; i ++) {
						if ($scope.loadedFlightCrew[i].id == resultCrew.id) {
							$scope.loadedFlightCrew[i] = resultCrew;
						}
					}
				}
			}
		});
	}; 
	
	
	$scope.deleteCrew = function (crew) {
		var modalInstance = $modal.open({
	        templateUrl: 'deleteCrewModal.html',
	        controller: 'deleteCrewCtrl',
	        resolve: {
	          crew_object: function () {
	            return crew;
	          }
	        }
	    });
		
		modalInstance.result.then(function (result) {
			if (result) {
				if ($scope.activePage == 'cabin') {
					$scope.hardReloadCabinPage();
				} else if ($scope.activePage == 'flight') {
					$scope.hardReloadFlightPage();
				}
			}
		});
	}; 
	
	$scope.advancedSearch = function (type) {
		var modalInstance = $modal.open({
	        templateUrl: 'advancedSearchModal.html',
	        controller: 'advancedSearchCtrl',
	        resolve: {
	          type: function () {
	            return type;
	          }
	        }
	    });
		
		modalInstance.result.then(function (result) {
			if (result instanceof Object) {
				$scope.advancedSearchDone = true;
				$scope.advancedSearchObject = result;
				$http.post('rest/api/crew/advanced_search/page/1/' + type, result).then(function (data) {
					$scope.cabinCrewNum = data.data.totalElements;
					$scope.loadedCabinCrew = data.data.content;
				});
			}
		});
	};
	
	$scope.modalCancelAdvancedSearch = function () {
		$scope.advancedSearchDone = false;
		$scope.advancedSearchObject = {};
		if ($scope.activePage == 'cabin') {
			$scope.hardReloadCabinPage();
		} else {
			$scope.hardReloadFlightPage();
		}
	};

	
});

cmsBackoffice.controller('advancedSearchCtrl',
		function($scope, $http, $modalInstance, type) {
	
	
	$scope.validationError = 0;
	$scope.criterias = [
	      {
	    	  "name" : "First name",
	    	  "param" : "firstName"
	      },
	      {
	    	  "name" : "Last name",
	    	  "param" : "lastName"
	      },
	      {
	    	  "name" : "Company ID",
	    	  "param" : "companyId"
	      }
	];
	$scope.selectedCriteria = $scope.criterias[0];
	$scope.criteriaParam = $scope.criterias[0].param;
	$scope.changeCriteria = function (crit) {
		$scope.criteriaParam = crit.param;
	};
	
	$scope.changeCriteriaValue = function (val) {
		$scope.criteriaValue = val;
	}
	
	
	if (type == 'cabin_crew') {
		$scope.crewType = "Cabin crew";
	} else {
		$scope.crewType = "Flight crew";
	}
	
	$scope.confirm = function () {
		$scope.validationError = 0;
		if ($scope.criteriaValue == undefined || $scope.criteriaValue == '') {
			$scope.validationError = 1;
			return;
		}
		$modalInstance.close({
			'criteriaParam' : $scope.criteriaParam,
			'criteriaValue' : $scope.criteriaValue
		});
	};
	
	
	$scope.closeModal = function () {
		$modalInstance.dismiss('cancel');
	};

});

cmsBackoffice.controller('deleteCrewCtrl',
		function($scope, $http, $modalInstance, crew_object) {
	
	$scope.closeModal = function () {
		$modalInstance.dismiss('cancel');
	};
	
	$scope.deleting = false;
	$scope.error = false;
	$scope.confirm = function () {
		$scope.error = false;
		$scope.deleting = true;
		$http.delete('/rest/api/crew/delete/' + crew_object.crewType + "/" + crew_object.id, $scope.newCrew).then(function (data) {
			if (data && data.status == 200) {
				$modalInstance.close(true);
				$scope.error = false;
				$scope.deleting = false;
			} else {
				$scope.error = true;
				$scope.deleting = false;
			}
		}, function (data) {
			$scope.error = true;
			$scope.deleting = false;			
		});
	};
});


cmsBackoffice.controller('editCrewCtrl',
		function($scope, $http, $modalInstance, crew_object) {
	
	$scope.newCrew = crew_object;
	
	$scope.countries = [];
	$http.get('backoffice/countries.json')
    		.then(function(res){
    	$scope.countries = res.data;
    });
	
	$scope.languages = [];
	$http.get('backoffice/languages.json')
    		.then(function(res){
    	$scope.languages = res.data;                
    });
	
	$scope.selectedLanguages = [];
	$scope.changeItLanguage = function (lang) {
		for (var i = 0; i < $scope.newCrew.languagesSpoken.length; i++) {
			if ($scope.newCrew.languagesSpoken[i] == lang.code) {
				return;
			}
		}
		$scope.newCrew.languagesSpoken.push(lang);
	};
	
	$scope.removeLanguage = function (lang) {
		for (var j = 0; j < $scope.newCrew.languagesSpoken.length; j++) {
			if ($scope.newCrew.languagesSpoken[j] == lang) {
				$scope.newCrew.languagesSpoken.splice(j,1);
				return;
			}
		}
	};
	
	$scope.unassignRoute = function (routeId) {
		$http.post('/rest/api/crew/unassign_route/' + $scope.newCrew.crewType + '/' +  
				$scope.newCrew.id + '/' + routeId, {}).then(function (data) {
			for (var i = 0; i < $scope.newCrew.assignedRoutes.length; i ++) {
				if ($scope.newCrew.assignedRoutes[i].routeId == routeId) {
					$scope.newCrew.assignedRoutes.splice(i, 1);
					return;
				}
			}
		});
	};
	
	$scope.validationError = 0;
	
	$scope.saveCrewChanges = function () {
		$http.post('/rest/api/crew/update', {
			'firstName' :  $scope.newCrew.firstName,
			'lastName' : $scope.newCrew.lastName,
			'username' : $scope.newCrew.username,
			'password' : $scope.newCrew.password,
			'mail' : $scope.newCrew.mail,
			'crewType' : $scope.newCrew.crewType,
			'countryOfOrigin' : $scope.newCrew.countryOfOrigin,
			'companyId' : $scope.newCrew.companyId,
			'languagesSpoken' : $scope.newCrew.languagesSpoken
		}).then(function (data) {
			if (data.status == 200) {
				$modalInstance.close(data.data);
			}
		});	
	}
	
	$scope.closeModal = function () {
		$modalInstance.dismiss('cancel');
	};
	
});

cmsBackoffice.controller('addNewCrewCtrl',
		function($scope, $http, $modalInstance, type) {
	
	$scope.type = type;
	
	$scope.newCrew = {
		"crewType" : type,
		"firstName" : "",
		"lastName" : "",
		"companyId" : "",
		"mail" : "",
		"languagesSpoken" : [],
		"countryOfOrigin" : ""
	};
	
	$scope.validationError = 0;

	$scope.countries = [];
	$http.get('backoffice/countries.json')
    		.then(function(res){
    	$scope.countries = res.data;
    	$scope.countryOfOrigin = $scope.countries[0].code;
    	$scope.selectedCountryOfOrigin =  $scope.countries[0].code;
    });
	
	$scope.languages = [];
	$http.get('backoffice/languages.json')
    		.then(function(res){
    	$scope.languages = res.data;                
    });
	
	
	$scope.selectedLanguages = [];
	$scope.changeItLanguage = function (lang) {
		for (var i = 0; i < $scope.selectedLanguages.length; i++) {
			if ($scope.selectedLanguages[i].code == lang.code) {
				return;
			}
		}
		$scope.selectedLanguages.push(lang);
	};
	
	$scope.removeLang = function (lang) {
		for (var i = 0; i < $scope.selectedLanguages.length; i++) {
			if ($scope.selectedLanguages[i].code == lang.code) {
				$scope.selectedLanguages.splice(i, 1);
				return;
			}
		}
	};
	
	
	$scope.changeIt = function (country) {
		$scope.selectedCountryOfOrigin = country;
	};
	
	$scope.generateCountryFlag = function (code) {
		return "http://flags.fmcdn.net/data/flags/normal/" + code.toLowerCase() + ".png";
	};
	
	$scope.closeModal = function () {
		$modalInstance.dismiss('cancel');
	};
	
	$scope.confirm = function () {
		$scope.validationError = 0;
		$scope.newCrew.countryOfOrigin = $scope.selectedCountryOfOrigin;
		if ($scope.validate() != 0) {
			return;
		}
		for (var j = 0; j < $scope.selectedLanguages.length; j ++) {
			$scope.newCrew.languagesSpoken.push($scope.selectedLanguages[j].code);
		}
		$scope.addingCrew = true;
		$http.post('/rest/api/crew/create', $scope.newCrew).then(function (data) {
			$scope.addingCrew = false;
			if (data && data.status == 201) {
				$scope.validationError = 0;
				$modalInstance.close(true);
			} else {
				$scope.validationError = -100;
			}
		}, function (data) {
			$scope.addingCrew = false;
			$scope.validationError = -100;
		});
	};
	
	
	$scope.validate = function () {
		if ($scope.newCrew.firstName.length < 3 || 
				$scope.newCrew.lastName.length < 3 ||
				$scope.newCrew.companyId.length < 3 ||
				$scope.newCrew.mail.length < 3) {
			$scope.validationError = 1;
			return 1;
		}
		if ($scope.selectedLanguages.length == 0) {
			$scope.validationError = 2;
			return 2;
		}
		return 0;
	};
	
	$scope.generateErrorMsg = function (error) {
		if (error == 1) {
			return "Please fill all the data";
		} else if (error == 2) {
			return "Please select at least one language spoken by the crew.";
		} else if (error == -100) {
			return "Internal error occured.";
		}
	};
	
	
});
