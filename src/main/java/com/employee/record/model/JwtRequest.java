package com.employee.record.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Data
@AllArgsConstructor
public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
}
