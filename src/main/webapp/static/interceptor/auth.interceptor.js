'use strict';

timezoneApp.factory('authInterceptorService', 
	['$q','$location', 
	function ($q, $location)
	{
	    var responseError = function (rejection) {
	        if (rejection.status === 403 || rejection.status === 401) {
	            $location.path('login');
	        }
	        return $q.reject(rejection);
	    };
	
	    return {
	        responseError: responseError
	    };
	}
]);
