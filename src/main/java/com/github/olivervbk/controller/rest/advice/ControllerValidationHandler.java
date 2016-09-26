package com.github.olivervbk.controller.rest.advice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author oliver.kuster
 * @version 1.0 Created on 22 de jun de 2016
 */
@ControllerAdvice
public class ControllerValidationHandler
{

	@ExceptionHandler( MethodArgumentNotValidException.class )
	@ResponseStatus( HttpStatus.BAD_REQUEST )
	@ResponseBody
	public Map<String, String> processValidationError( final MethodArgumentNotValidException ex )
	{
		final Map<String, String> errorsMap = new HashMap<>();

		final BindingResult result = ex.getBindingResult();
		final List<FieldError> fieldErrors = result.getFieldErrors();
		for ( final FieldError fieldError : fieldErrors )
		{
			final String message = fieldError.getDefaultMessage();
			final String field = fieldError.getField();

			errorsMap.put( field, message );
		} // for

		return errorsMap;
	}
}
