package com.employee.record.dto;

import lombok.Builder;
import lombok.NoArgsConstructor;


import lombok.AllArgsConstructor;
import lombok.Data;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeDTO {

	private String Name;
	private String Surname;
	private String Email;
	private String Address;
	private Double salary;
	private CompanyDTO companyDTO;
}
