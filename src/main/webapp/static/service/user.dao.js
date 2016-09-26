'use strict';

timezoneApp.factory( "UserDao", [
	'$resource', function( $resource )
	{
		return $resource( "/rest/user/:id", null,
		{
			'update' :
			{
				method : 'PUT'
			},
		} );
	}
] );
