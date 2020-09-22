package com.employee.record.exception;

import com.employee.record.enums.NotFoundErrorEnum;

import lombok.Data;

@Data
public class AverageSalaryIsNullException extends EmployeeRecordsAbstractException {

	private static final long serialVersionUID = 1L;
	
	public AverageSalaryIsNullException(NotFoundErrorEnum notFoundErrorEnum) {
		super(notFoundErrorEnum.getCode(), notFoundErrorEnum.getMessage().toLowerCase());
	}
}
