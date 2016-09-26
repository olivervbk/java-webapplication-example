'use strict';

timezoneApp.controller( 'NavController', [
	'$scope', '$location', '$rootScope', 'AuthenticationService',
	function( $scope, $location, $rootScope, $authService )
	{
		$scope.isActive = function( viewLocation )
		{
			return viewLocation === $location.path();
		};

		$scope.isLoggedIn = function()
		{
			return $rootScope.globals.currentUser != undefined;
		};

		$scope.isAdmin = function()
		{
			var currentUser = $rootScope.globals.currentUser;
			if ( currentUser != undefined )
			{
				if ( currentUser.role == "ADMIN" )
				{
					return true;
				}
			}
			return false;
		};

		$scope.logOut = function()
		{
			$authService.ClearCredentials();
		};
	}
] );
