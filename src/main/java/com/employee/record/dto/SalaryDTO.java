package com.employee.record.dto;

import lombok.Builder;
import lombok.NoArgsConstructor;


import lombok.AllArgsConstructor;
import lombok.Data;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SalaryDTO {

	private String companyName;
	private Double averageSalary;
}
