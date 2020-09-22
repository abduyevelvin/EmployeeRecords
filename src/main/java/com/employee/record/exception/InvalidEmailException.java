package com.employee.record.exception;

import com.employee.record.enums.InvalidEmailEnum;

import lombok.Data;

@Data
public class InvalidEmailException extends EmployeeRecordsAbstractException {

	private static final long serialVersionUID = 1L;
	
	public InvalidEmailException(InvalidEmailEnum invalidEmailEnum) {
		super(invalidEmailEnum.getCode(), invalidEmailEnum.getMessage().toLowerCase());
	}
}
