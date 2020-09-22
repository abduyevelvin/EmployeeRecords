package com.employee.record.exception;


import lombok.Data;

@Data
public class EmployeeRecordsAbstractException extends RuntimeException {

	private static final long serialVersionUID = 1L;
    protected final Integer errorCode;
    protected final String message;
    
    public EmployeeRecordsAbstractException(Integer errorCode, String message) {
    	this.errorCode = errorCode;
        this.message = message;
	}
}
