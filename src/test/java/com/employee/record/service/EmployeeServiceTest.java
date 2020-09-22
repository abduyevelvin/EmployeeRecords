package com.employee.record.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import com.employee.record.dto.CompanyDTO;
import com.employee.record.dto.EmployeeDTO;
import com.employee.record.dto.ResponseDTO;
import com.employee.record.model.Company;
import com.employee.record.model.Employee;
import com.employee.record.repository.IEmployeeRepository;

@SpringBootTest
@ActiveProfiles("test")
public class EmployeeServiceTest {

	private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceTest.class);

	@Autowired
	private IEmployeeService employeeService;

	@MockBean(name = "employeeRepo")
	private IEmployeeRepository employeeRepo;

	@SuppressWarnings("static-access")
	@Test
	public void getAllEmployeesByCompany() {
		Long companyId = 1L;
		Long employeeId = 1L;
		List<Employee> employees = new ArrayList<>();
		List<EmployeeDTO> employeesDTO = new ArrayList<>();
		Company company = new Company(companyId, "Test");
		Employee employee = new Employee(employeeId, "Test", "Test", "test@test.com", "Test Address", 1234.55, company);
		employees.add(employee);
		Page<Employee> pagedResponse = new PageImpl<>(employees);

		Mockito.when(employeeRepo.findByCompanyId(companyId, null)).thenReturn(pagedResponse);

		CompanyDTO companyDTO = new CompanyDTO().builder().companyName(company.getName()).build();

		EmployeeDTO employeeDTO = new EmployeeDTO().builder().Name(employee.getName()).Surname(employee.getSurname())
				.Email(employee.getEmail()).Address(employee.getAddress()).salary(employee.getSalary())
				.companyDTO(companyDTO).build();
		employeesDTO.add(employeeDTO);

		ResponseDTO<List<EmployeeDTO>> response = ResponseDTO.<List<EmployeeDTO>>builder().data(employeesDTO)
				.message("Success").status(HttpStatus.OK.value()).build();

		LOG.info("The mock response: " + response);
		LOG.info("The mocked service response: " + employeeService.getAllEmployeesByCompany(companyId, null));

		Assertions.assertEquals(response, employeeService.getAllEmployeesByCompany(companyId, null));
	}

	@SuppressWarnings("static-access")
	@Test
	public void editEmployee() {
		Long companyId = 1L;
		Long employeeId = 1L;
		Company company = new Company(companyId, "Test");
		Employee employee = new Employee(employeeId, "Edit", "Edit", "edit@test.com", "Edit Address", 1235.55, company);
		Optional<Employee> optionalEmployee = Optional.of(employee);

		Mockito.when(employeeRepo.findByIdAndCompanyId(companyId, employeeId)).thenReturn(optionalEmployee);
		Mockito.when(employeeRepo.save(employee)).thenReturn(employee);

		CompanyDTO companyDTO = new CompanyDTO().builder().companyName(company.getName()).build();

		EmployeeDTO employeeDTO = new EmployeeDTO().builder().Name(employee.getName()).Surname(employee.getSurname())
				.Email(employee.getEmail()).Address(employee.getAddress()).salary(employee.getSalary())
				.companyDTO(companyDTO).build();

		ResponseDTO<EmployeeDTO> response = ResponseDTO.<EmployeeDTO>builder().data(employeeDTO).message("Success")
				.status(HttpStatus.OK.value()).build();

		LOG.info("The mock response: " + response);
		LOG.info("The mocked service response: " + employeeService.editEmployee(companyId, employeeId, employee));

		Assertions.assertEquals(response, employeeService.editEmployee(companyId, employeeId, employee));
	}
}
