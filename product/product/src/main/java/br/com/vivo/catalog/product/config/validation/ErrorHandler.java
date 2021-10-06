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

import br.com.vivo.catalog.product.dto.ErrorDTO;

@RestControllerAdvice
public class ErrorHandler {
	
	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorDTO handle(MethodArgumentNotValidException exception) {
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
		return new ErrorDTO(HttpStatus.BAD_REQUEST.value(), errorMessage);
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public ErrorDTO handleRequestParamErrors(ConstraintViolationException exception) {
		return new ErrorDTO(HttpStatus.BAD_REQUEST.value(), new StringBuilder(exception.getLocalizedMessage()));
	}
	
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ErrorDTO handleGlobalErrors(Exception exception) {
		return new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), new StringBuilder(exception.getLocalizedMessage()));
	}
	
}
