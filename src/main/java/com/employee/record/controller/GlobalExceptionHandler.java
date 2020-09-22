package com.employee.record.controller;

import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.employee.record.dto.ErrorDTO;
import com.employee.record.exception.AverageSalaryIsNullException;
import com.employee.record.exception.CompanyNotFoundException;
import com.employee.record.exception.EmployeeNotFoundException;
import com.employee.record.exception.InvalidEmailException;

@ControllerAdvice
@RestController
@CrossOrigin
public class GlobalExceptionHandler {

	@ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({EmployeeNotFoundException.class})
    public Object handleException(EmployeeNotFoundException exception, Locale locale) {
        return ErrorDTO
                .builder()
                .code(exception.getErrorCode())
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
    }
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({CompanyNotFoundException.class})
    public Object handleException(CompanyNotFoundException exception, Locale locale) {
        return ErrorDTO
                .builder()
                .code(exception.getErrorCode())
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
    }
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidEmailException.class})
    public Object handleException(InvalidEmailException exception, Locale locale) {
        return ErrorDTO
                .builder()
                .code(exception.getErrorCode())
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
    }
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({AverageSalaryIsNullException.class})
    public Object handleException(AverageSalaryIsNullException exception, Locale locale) {
        return ErrorDTO
                .builder()
                .code(exception.getErrorCode())
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
    }
}
