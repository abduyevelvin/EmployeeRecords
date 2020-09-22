package com.employee.record.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Data
@AllArgsConstructor
public class EmployeeRequest {

	private String name;
	private String surname;
	private String address;
}
