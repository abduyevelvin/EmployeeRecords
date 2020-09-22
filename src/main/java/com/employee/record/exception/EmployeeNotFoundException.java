package com.employee.record.exception;

import com.employee.record.enums.NotFoundErrorEnum;

import lombok.Data;

@Data
public class EmployeeNotFoundException extends EmployeeRecordsAbstractException {

	private static final long serialVersionUID = 1L;
	
	public EmployeeNotFoundException(NotFoundErrorEnum notFoundErrorEnum) {
		super(notFoundErrorEnum.getCode(), notFoundErrorEnum.getMessage().toLowerCase());
	}
}
