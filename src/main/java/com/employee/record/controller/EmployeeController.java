package com.employee.record.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.record.dto.EmployeeDTO;
import com.employee.record.dto.ResponseDTO;
import com.employee.record.dto.SalaryDTO;
import com.employee.record.model.Employee;
import com.employee.record.model.EmployeeRequest;
import com.employee.record.service.IEmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping("/api")
@Api("Employee Controller")
public class EmployeeController {

	private IEmployeeService employeeService;
	
	@Value("${spring.datasource.platform}")
	private String platform;

	public EmployeeController(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@PostMapping("/companies/{companyId}/employees")
	@ApiOperation(value = "return the created employee", notes = "need provide the company id and employee data for saving")
	public ResponseDTO<EmployeeDTO> createEmployee(@PathVariable("companyId") Long companyId,
			@Valid @RequestBody Employee employee) {

		return employeeService.createEmployee(companyId, employee);
	}

	@GetMapping("/employees")
	@ApiOperation(value = "return all employees", notes = "page number or size can be provided as '?page=0&size=2'")
	public ResponseDTO<List<EmployeeDTO>> getAllEmployees(Pageable pageable) {

		return employeeService.getAllEmployees(pageable);
	}

	@GetMapping("/companies/{companyId}/employees")
	@ApiOperation(value = "return all employees by company id", notes = "page number or size can be provided as '?page=0&size=2'")
	public ResponseDTO<List<EmployeeDTO>> getAllEmployees(@PathVariable("companyId") Long companyId,
			Pageable pageable) {

		return employeeService.getAllEmployeesByCompany(companyId, pageable);
	}

	@GetMapping("/companies/{companyId}/employees/{employeeId}")
	@ApiOperation(value = "return the employee by the company id", notes = "need provide the company id and the employee in the path to get the employee")
	public ResponseDTO<EmployeeDTO> getEmployee(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId) {

		return employeeService.getEmployee(companyId, employeeId);
	}
	
	@GetMapping("/employees/{companyId}/averageSalary")
	@ApiOperation(value = "return the average salary by the company id", notes = "need provide the company id in the path to get the average salary")
	public ResponseDTO<SalaryDTO> getAverageSalary(@PathVariable("companyId") Long companyId) {

		if (platform.equalsIgnoreCase("mysql")) {
			return employeeService.getAverageSalaryMySql(companyId);
		} else {
			return employeeService.getAverageSalaryH2(companyId);
		}		
	}
	
	@PutMapping("/companies/{companyId}/employees/{employeeId}")
	@ApiOperation(value = "return the updated employee by the company id", notes = "need provide the company id and the employee in the path to edit the employee")
	public ResponseDTO<EmployeeDTO> editEmployee(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId, @Valid @RequestBody Employee employee) {

		return employeeService.editEmployee(companyId, employeeId, employee);
	}
	
	@PatchMapping("/employees/{employeeId}")
	@ApiOperation(value = "return the updated employee by the employee id", notes = "need provide the employee in the path to edit the employee")
	public ResponseDTO<EmployeeDTO> editEmployee(@PathVariable("employeeId") Long employeeId, @Valid @RequestBody EmployeeRequest employee) {

		return employeeService.editEmployeePartially(employeeId, employee);
	}
	
	@DeleteMapping("/companies/{companyId}/employees/{employeeId}")
	@ApiOperation(value = "return the string message", notes = "need provide the company id and the employee in the path to delete the employee")
	public ResponseDTO<String> deleteEmployee(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId) {

		return employeeService.deleteEmployee(companyId, employeeId);
	}
}
