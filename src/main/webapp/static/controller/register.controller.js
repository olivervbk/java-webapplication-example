'use strict';

timezoneApp.controller( 'RegisterController', [
	'$scope', '$location', 'RegisterDao', function( $scope, $location, RegisterDao )
	{
		$scope.register = function()
		{
			$scope.dataLoading = true;

			RegisterDao.save( $scope.user, function( response )
			{
				$location.path( '/login' );
			} );
		};
	}
] );
