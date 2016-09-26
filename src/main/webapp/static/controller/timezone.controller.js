'use strict';

timezoneApp.controller( 'TimezoneController', [
	'$scope', '$location', '$route', '$interval', 'TimezoneDao',
	function( $scope, $location, $route, $interval, TimezoneDao )
	{

		var setupClock = function( item )
		{
			TimezoneDao.time(
			{
				id : item.id
			} ).then( function( time )
			{
				var date = new Date( time.data * 1000 ); // this is in local timezone
				var adjustedDate = new Date( date.getTime() + date.getTimezoneOffset() * 60000 );
				item.clock = adjustedDate;
				
				var clockUpdateCallback = function()
				{
					item.clock = new Date(item.clock.getTime() + 1000);
				};
				
				var promise = $interval( clockUpdateCallback, 1000 );
				item.stop = function()
				{
					$interval.cancel( promise );
					item.stop = function()
					{
					};
				};
				
			} );
		};

		$scope.timezones = [];
		$scope.tzFormErrors = [];
		
		var clearValidationErrorMessages = function() {
	    	$scope.tzForm.$setPristine(true);
	    	$scope.tzFormErrors = [];
	    };
		
		var setValidationErrorMessages = function (errors){
			$scope.tzFormErrors = errors;
			
			Object.keys(errors).forEach(function (key)
			{ 
				var details = errors[key];
				if (details.code in ['maxlength', 'required', 'minlength']){
					$scope.tzForm[key].$setValidity(details.code, false);
				}else{
					$scope.tzForm[key].$setValidity('backend', false);
				}
			});
		};

		var clearClocks = function()
		{
			$scope.timezones.forEach( function( item )
			{
				console.log( "stop id:" + item.id );
				item.stop();
			} );
		};

		$scope.$on( "$destroy", function()
		{
			console.log( "destroy!" );
			clearClocks();

		} );

		$scope.showAll = function()
		{
			clearClocks();
			TimezoneDao.query( function( data )
			{
				$scope.timezones = data;
				$scope.timezones.forEach( function( item )
				{
					setupClock( item );
				} );
			} );
		};
		// show all by default
		$scope.showAll();

		$scope.remove = function( timezone )
		{
			timezone.stop();

			TimezoneDao.remove(
			{
				id : timezone.id
			}, function()
			{
				$route.reload();
			} );
		};

		$scope.save = function( timezone )
		{
			if ( timezone.id != null )
			{
				TimezoneDao.update(
				{
					id : timezone.id
				}, timezone, function( savedTz )
				{
					$scope.timezone = null;
				}, function (error){
					setValidationErrorMessages( error.data );
				} );
			}
			else
			{
				TimezoneDao.save( timezone, function( savedTz ) 
				{
					setupClock( savedTz );
					$scope.timezones.push( savedTz );
					$scope.timezone = null;
				}, function (error)
				{
					setValidationErrorMessages( error.data );
				} );
			}
		};

		$scope.edit = function( timezone )
		{
			clearValidationErrorMessages();
			$scope.timezone = timezone;
			// angular.copy( timezone );
		};

		$scope.filter = function( tzFilter )
		{
			clearClocks();
			TimezoneDao.search(
			{
				name : tzFilter.name
			}, function( data )
			{
				$scope.timezones = data;
				$scope.timezones.forEach( function( item )
				{
					setupClock( item );
				} );
			} );
		};
	}
] );
