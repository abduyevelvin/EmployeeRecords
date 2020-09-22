package com.employee.record.enums;

import lombok.Getter;

@Getter
public enum InvalidEmailEnum {

	INVALID_EMAIL(311, "invalid_email");
	
	private int code;
    private String message;

    private InvalidEmailEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
