'use strict';

timezoneApp.controller( 'UserController', 
	['$scope', '$location', '$route', '$interval', 'UserDao', 
	function( $scope, $location, $route, $interval, UserDao ) 
	{
		$scope.users = {};

		$scope.showAll = function()
		{
			UserDao.query( function( data )
			{
				$scope.users = data;
			} );
		};
		// show all by default
		$scope.showAll();

		$scope.remove = function( timezone )
		{
			UserDao.remove(
			{
				id : timezone.id
			}, function()
			{
				$route.reload();
			} );
		};

		$scope.save = function( user )
		{
			if ( user.id != null )
			{
				UserDao.update(
				{
					id : user.id
				}, user, function( savedUser )
				{
					$scope.user = null;
				} );
			}
			else
			{
				UserDao.save( user, function( savedUser )
				{
					$scope.users.push( savedUser );
					$scope.user = null;
				} );
			}
		};

		$scope.edit = function( user )
		{
			$scope.user = user;
			// angular.copy( timezone );
		};
	}
] );
