package br.com.vivo.catalog.product.config.validation;

import java.util.Iterator;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.vivo.catalog.product.dto.ErrorValidationDTO;

@RestControllerAdvice
public class ErrorValidationHandler {
	
	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorValidationDTO handle(MethodArgumentNotValidException exception) {
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		Iterator<FieldError> iteratorFieldErrors = fieldErrors.iterator();
		StringBuilder errorMessage = new StringBuilder();
		while(iteratorFieldErrors.hasNext()) {
			String message = messageSource.getMessage(iteratorFieldErrors.next(), LocaleContextHolder.getLocale());
			errorMessage.append(message);
			if(iteratorFieldErrors.hasNext()) {
				errorMessage.append(", ");
			} else {
				errorMessage.append(".");
			}
		}
		ErrorValidationDTO error = new ErrorValidationDTO(HttpStatus.BAD_REQUEST.value(), errorMessage);
		return error;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public ErrorValidationDTO handleRequestParamErrors(ConstraintViolationException exception) {
		ErrorValidationDTO error = new ErrorValidationDTO(HttpStatus.BAD_REQUEST.value(), new StringBuilder(exception.getLocalizedMessage()));
		return error;
	}
	
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ErrorValidationDTO handleGlobalErrors(Exception exception) {
		ErrorValidationDTO error = new ErrorValidationDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), new StringBuilder(exception.getLocalizedMessage()));
		return error;
	}
	
}
