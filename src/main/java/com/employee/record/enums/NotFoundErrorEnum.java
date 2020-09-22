package com.employee.record.enums;

import lombok.Getter;

@Getter
public enum NotFoundErrorEnum {

	EMPLOYEES_NOT_FOUND(121, "employees_not_found"),
	EMPLOYEE_NOT_FOUND(122, "employee_not_found"),
	COMPANIES_NOT_FOUND(221, "companies_not_found"),
	COMPANY_NOT_FOUND(222, "company_not_found"),
	AVERAGE_SALARY_IS_NULL(444, "average_salary_is_null");
	
	private int code;
    private String message;

    private NotFoundErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
