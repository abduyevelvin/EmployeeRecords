package com.employee.record.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String jwttoken;
}
