'use strict';

var productAppContrl = angular.module('productApp.controllers',[]);

productAppContrl.controller('productCtrl', ['$scope', '$http', function ($scope, $http) {
	

function getAllProducts(){
	$http.get('json/index.json').then(function(response) {
		$scope.products = response.data;
	});
}

getAllProducts();
	
}]);
