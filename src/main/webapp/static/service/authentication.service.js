'use strict';

timezoneApp.factory( 'AuthenticationService', [
	'$http', '$cookies', '$rootScope', '$timeout', '$window', function( $http, $cookies, $rootScope, $timeout, $window )
	{
		var service = {};

		service.Login = function( username, password, callback )
		{
			var authHeader = 'Basic ' + $window.btoa( username + ':' + password );
			var req =
			{
				method : 'POST',
				url : '/rest/user/auth',
				headers :
				{
					'Authorization' : authHeader
				}
			};

			$http( req ).success( function( data )
			{
				callback(
				{
					success : true,
					data : data
				} );
			} ).error( function( response )
			{
				callback(
				{
					message : 'Failed'
				} );
			} )
		};

		service.SetCredentials = function( username, password, user )
		{
			var authdata = $window.btoa( username + ':' + password );

			$rootScope.globals =
			{
				currentUser :
				{
					username : username,
					authdata : authdata,
					role : user.role
				}
			};

			$http.defaults.headers.common[ 'Authorization' ] = 'Basic ' + authdata; // jshint ignore:line
			$cookies.put( 'globals', $rootScope.globals );
		};

		service.ClearCredentials = function()
		{
			$rootScope.globals = {};
			$cookies.remove( 'globals' );
			$http.defaults.headers.common.Authorization = 'Basic ';
		};

		return service;
	}
] );
