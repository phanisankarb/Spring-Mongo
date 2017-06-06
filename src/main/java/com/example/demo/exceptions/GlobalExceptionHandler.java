package com.example.demo.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.util.GateWayResponse;

/**
 * Global exception handler for Rest API calls, preparing proper exception details and returning the Response entity
 * @author pbattula
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Object> handleIOException(BadRequestException bre, final WebRequest request) {
		log.info(bre.getClass().getName());
		//
		final GateWayResponse<Void> apiError = new GateWayResponse<>(false, HttpStatus.BAD_REQUEST, bre.getMessage(), "");
		return handleExceptionInternal(bre, apiError, new HttpHeaders(), apiError.getHttpStatus(), request);
	}

	// 400

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		log.info(ex.getClass().getName());
		//
		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		final GateWayResponse<Void> apiError = new GateWayResponse<>(false, HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
				errors);
		return handleExceptionInternal(ex, apiError, headers, apiError.getHttpStatus(), request);
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException bre, final WebRequest request) {
		log.info(bre.getClass().getName());
		final GateWayResponse<Void> apiError = new GateWayResponse<>(false, HttpStatus.UNAUTHORIZED, bre.getMessage(), "Given token is invalid");
		return handleExceptionInternal(bre, apiError, new HttpHeaders(), apiError.getHttpStatus(), request);
	}


	@Override
	protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		log.info(ex.getClass().getName());
		//
		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		final GateWayResponse<Void> apiError = new GateWayResponse<>(false, HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
				errors);
		return handleExceptionInternal(ex, apiError, headers, apiError.getHttpStatus(), request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		log.info(ex.getClass().getName());
		//
		final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type "
				+ ex.getRequiredType();

		final GateWayResponse<Void> apiError = new GateWayResponse<>(false, HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
				error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getHttpStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		log.info(ex.getClass().getName());
		//
		final String error = ex.getRequestPartName() + " part is missing";
		final GateWayResponse<Void> apiError = new GateWayResponse<>(false, HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
				error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getHttpStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status,
			final WebRequest request) {
		log.info(ex.getClass().getName());
		//
		final String error = ex.getParameterName() + " parameter is missing";
		final GateWayResponse<Void> apiError = new GateWayResponse<>(false, HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
				error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getHttpStatus());
	}

	//

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex,
			final WebRequest request) {
		log.info(ex.getClass().getName());
		//
		final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

		final GateWayResponse<Void> apiError = new GateWayResponse<>(false, HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
				error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getHttpStatus());
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex,
			final WebRequest request) {
		log.info(ex.getClass().getName());
		//
		final List<String> errors = new ArrayList<String>();
		for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
					+ violation.getMessage());
		}

		final GateWayResponse<Void> apiError = new GateWayResponse<>(false, HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
				errors);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getHttpStatus());
	}

	// 404

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		log.info(ex.getClass().getName());
		//
		final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

		final GateWayResponse<Void> apiError = new GateWayResponse<>(false, HttpStatus.NOT_FOUND, ex.getLocalizedMessage(),
				error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getHttpStatus());
	}

	// 405

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status,
			final WebRequest request) {
		log.info(ex.getClass().getName());
		//
		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" method is not supported for this request. Supported methods are ");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

		final GateWayResponse<Void> apiError = new GateWayResponse<>(false, HttpStatus.METHOD_NOT_ALLOWED,
				ex.getLocalizedMessage(), builder.toString());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getHttpStatus());
	}

	// 415

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		log.info(ex.getClass().getName());
		//
		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

		final GateWayResponse<Void> apiError = new GateWayResponse<>(false, HttpStatus.UNSUPPORTED_MEDIA_TYPE,
				ex.getLocalizedMessage(), builder.substring(0, builder.length() - 2));
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getHttpStatus());
	}

	// 500

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
		log.info(ex.getClass().getName());
		log.error("error", ex);

		final GateWayResponse<Void> apiError = new GateWayResponse<>(false, HttpStatus.INTERNAL_SERVER_ERROR,
				ex.getLocalizedMessage(), "error occurred");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getHttpStatus());
	}

}
