'use strict';

timezoneApp.factory("TimezoneDao", 
	['$resource', '$http', 
	function($resource, $http)
	{
		var Timezone = $resource("/rest/timezone/:id", null, 
				{
					'update': {method: 'PUT' },
					'search': { method: 'GET', url: "/rest/timezone/search", params: {}, isArray: true }
					//'time': { method: 'GET', url: "/rest/timezone/:id/time", params: {} }
				});
		Timezone.time = function(tz){
			return $http( {
			    url: "/rest/timezone/"+tz.id+"/time", 
			    method: "GET"
			 });
		};
		return Timezone;
	}
]);