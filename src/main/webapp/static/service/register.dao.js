'use strict';

timezoneApp.factory( "RegisterDao", [
	'$resource', function( $resource )
	{
		return $resource( "/rest/register/:id" );
	}
] );
