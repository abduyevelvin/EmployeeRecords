package com.employee.record.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.employee.record.dto.EmployeeDTO;
import com.employee.record.dto.ResponseDTO;
import com.employee.record.dto.SalaryDTO;
import com.employee.record.model.Employee;
import com.employee.record.model.EmployeeRequest;

public interface IEmployeeService {

	ResponseDTO<EmployeeDTO> createEmployee(Long companyId, Employee employee);
	ResponseDTO<List<EmployeeDTO>> getAllEmployees(Pageable pageable);
	ResponseDTO<List<EmployeeDTO>> getAllEmployeesByCompany(Long companyId, Pageable pageable);
	ResponseDTO<EmployeeDTO> getEmployee(Long companyId, Long employeeId);
	ResponseDTO<EmployeeDTO> editEmployee(Long companyId, Long employeeId, Employee employee);
	ResponseDTO<String> deleteEmployee(Long companyId, Long employeeId);
	
	ResponseDTO<EmployeeDTO> editEmployeePartially(Long id, EmployeeRequest employee);
	
	ResponseDTO<SalaryDTO> getAverageSalaryMySql(Long companyId);
	ResponseDTO<SalaryDTO> getAverageSalaryH2(Long companyId);
}
