'use strict';

// declare modules
var timezoneApp = angular.module( "TimezoneApp", [
	'ngRoute', 'ngCookies', 'ngResource'
] );

timezoneApp.config( 
	['$routeProvider', '$httpProvider', 
	function( $routeProvider, $httpProvider )
	{
		// não mostrar popup em 401
		$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

		$routeProvider.when( '/login',
		{
			controller : 'LoginController',
			templateUrl : 'view/login.html'
		} )

		.when( '/timezone',
		{
			controller : 'TimezoneController',
			templateUrl : 'view/timezone.html'
		} )

		.when( '/user',
		{
			controller : 'UserController',
			templateUrl : 'view/user.html'
		} )

		.when( '/register',
		{
			controller : 'RegisterController',
			templateUrl : 'view/register.html'
		} )

		.otherwise(
		{
			redirectTo : '/login'
		} );
	}
] );

timezoneApp.run( 
	['$rootScope', '$location', '$cookies', '$http', 
	function( $rootScope, $location, $cookies, $http )
	{	
		// keep user logged in after page refresh
		$rootScope.globals = $cookies.get( 'globals' ) || {};
		if ( $rootScope.globals.currentUser )
		{
			$http.defaults.headers.common[ 'Authorization' ] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint
			// ignore:line
		}

		$rootScope.$on( '$locationChangeStart', function( event, next, current )
		{
			// redirect to login page if not logged in
			// redirect to login page if not logged in and trying to access a restricted page
			var restrictedPage = $.inArray( $location.path(), [
				'/login', '/register'
			] ) === -1;
			var loggedIn = $rootScope.globals.currentUser;
			if ( restrictedPage && !loggedIn )
			{
				$location.path( '/login' );
			}
		} );
	}
] );

timezoneApp.config(['$httpProvider', function($httpProvider) {
    $httpProvider.interceptors.push('authInterceptorService');
}]);
