package com.employee.record.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.employee.record.dto.CompanyDTO;
import com.employee.record.dto.EmployeeDTO;
import com.employee.record.dto.ResponseDTO;
import com.employee.record.dto.SalaryDTO;
import com.employee.record.enums.InvalidEmailEnum;
import com.employee.record.enums.NotFoundErrorEnum;
import com.employee.record.exception.AverageSalaryIsNullException;
import com.employee.record.exception.CompanyNotFoundException;
import com.employee.record.exception.EmployeeNotFoundException;
import com.employee.record.exception.InvalidEmailException;
import com.employee.record.model.Company;
import com.employee.record.model.Employee;
import com.employee.record.model.EmployeeRequest;
import com.employee.record.repository.ICompanyRepository;
import com.employee.record.repository.IEmployeeRepository;

@Service
public class EmployeeService implements IEmployeeService {

	private static final Logger LOG = LoggerFactory.getLogger(EmployeeService.class);
	private static final String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
	// initialize the Pattern object
	Pattern pattern = Pattern.compile(regex);

	private IEmployeeRepository employeeRepo;
	private ICompanyRepository companyRepo;

	public EmployeeService(IEmployeeRepository employeeRepo, ICompanyRepository companyRepo) {
		this.employeeRepo = employeeRepo;
		this.companyRepo = companyRepo;
	}

	@SuppressWarnings("static-access")
	@Override
	public ResponseDTO<EmployeeDTO> createEmployee(Long companyId, Employee employee) {
		Company company = companyRepo.findById(companyId).orElseThrow(() -> {
			LOG.warn(String.format("Cannot find the company by id: %s", companyId));
			return new CompanyNotFoundException(NotFoundErrorEnum.COMPANY_NOT_FOUND);
		});
		employee.setCompany(company);

		Matcher matcher = pattern.matcher(employee.getEmail());
		if (!matcher.matches()) {
			throw new InvalidEmailException(InvalidEmailEnum.INVALID_EMAIL);
		}

		Employee emp = employeeRepo.save(employee);
		CompanyDTO companyDTO = new CompanyDTO().builder().companyName(emp.getCompany().getName()).build();

		EmployeeDTO dto = new EmployeeDTO().builder().Name(emp.getName()).Surname(emp.getSurname())
				.Email(emp.getEmail()).Address(emp.getAddress()).salary(emp.getSalary()).companyDTO(companyDTO).build();

		LOG.info(String.format("The employee is created with id: %s", emp.getId()));
		return ResponseDTO.<EmployeeDTO>builder().data(dto).message("Success").status(HttpStatus.CREATED.value()).build();
	}

	@SuppressWarnings("static-access")
	@Override
	public ResponseDTO<List<EmployeeDTO>> getAllEmployees(Pageable pageable) {
		Page<Employee> employees = employeeRepo.findAll(pageable);

		if (employees == null || employees.isEmpty()) {
			LOG.warn("There are no employees");
			throw new EmployeeNotFoundException(NotFoundErrorEnum.EMPLOYEES_NOT_FOUND);
		}

		List<EmployeeDTO> dtos = employees.stream()
				.map(e -> new EmployeeDTO().builder().Name(e.getName()).Surname(e.getSurname()).Email(e.getEmail())
						.Address(e.getAddress()).salary(e.getSalary())
						.companyDTO(new CompanyDTO().builder().companyName(e.getCompany().getName()).build()).build())
				.collect(Collectors.toList());

		return ResponseDTO.<List<EmployeeDTO>>builder().data(dtos).message("Success").status(HttpStatus.OK.value()).build();
	}

	@SuppressWarnings("static-access")
	@Override
	public ResponseDTO<List<EmployeeDTO>> getAllEmployeesByCompany(Long companyId, Pageable pageable) {
		Page<Employee> employees = employeeRepo.findByCompanyId(companyId, pageable);

		if (employees == null || employees.isEmpty()) {
			LOG.warn(String.format("There are no employees for company id: %s", companyId));
			throw new EmployeeNotFoundException(NotFoundErrorEnum.EMPLOYEES_NOT_FOUND);
		}

		List<EmployeeDTO> dtos = employees.stream()
				.map(e -> new EmployeeDTO().builder().Name(e.getName()).Surname(e.getSurname()).Email(e.getEmail())
						.Address(e.getAddress()).salary(e.getSalary())
						.companyDTO(new CompanyDTO().builder().companyName(e.getCompany().getName()).build()).build())
				.collect(Collectors.toList());

		return ResponseDTO.<List<EmployeeDTO>>builder().data(dtos).message("Success").status(HttpStatus.OK.value()).build();
	}

	@SuppressWarnings("static-access")
	@Override
	public ResponseDTO<EmployeeDTO> getEmployee(Long companyId, Long employeeId) {
		Employee employee = employeeRepo.findByIdAndCompanyId(employeeId, companyId).orElseThrow(() -> {
			LOG.warn(String.format("Cannot find the employee by id: %s for the company by id: %s", employeeId,
					companyId));
			return new EmployeeNotFoundException(NotFoundErrorEnum.EMPLOYEE_NOT_FOUND);
		});

		EmployeeDTO dto = new EmployeeDTO().builder().Name(employee.getName()).Surname(employee.getSurname())
				.Email(employee.getEmail()).Address(employee.getAddress()).salary(employee.getSalary())
				.companyDTO(new CompanyDTO().builder().companyName(employee.getCompany().getName()).build()).build();

		return ResponseDTO.<EmployeeDTO>builder().data(dto).message("Success").status(HttpStatus.OK.value()).build();
	}

	@SuppressWarnings("static-access")
	@Override
	public ResponseDTO<EmployeeDTO> editEmployee(Long companyId, Long employeeId, Employee employee) {
		Employee emp = employeeRepo.findByIdAndCompanyId(employeeId, companyId).orElseThrow(() -> {
			LOG.warn(String.format("Cannot find the employee by id: %s for the company by id: %s", employeeId,
					companyId));
			return new EmployeeNotFoundException(NotFoundErrorEnum.EMPLOYEE_NOT_FOUND);
		});

		Matcher matcher = pattern.matcher(employee.getEmail());
		if (!matcher.matches()) {
			throw new InvalidEmailException(InvalidEmailEnum.INVALID_EMAIL);
		}

		emp.setName(employee.getName());
		emp.setSurname(employee.getSurname());
		emp.setEmail(employee.getEmail());
		emp.setAddress(employee.getAddress());
		emp.setSalary(employee.getSalary());

		Employee updated = employeeRepo.save(emp);

		EmployeeDTO dto = new EmployeeDTO().builder().Name(updated.getName()).Surname(updated.getSurname())
				.Email(updated.getEmail()).Address(updated.getAddress()).salary(updated.getSalary())
				.companyDTO(new CompanyDTO().builder().companyName(updated.getCompany().getName()).build()).build();

		LOG.warn(String.format("The employee by id: %s for the company by id: %s is edited!", employeeId, companyId));
		return ResponseDTO.<EmployeeDTO>builder().data(dto).message("Success").status(HttpStatus.OK.value()).build();
	}

	@Override
	public ResponseDTO<String> deleteEmployee(Long companyId, Long employeeId) {
		Employee employee = employeeRepo.findByIdAndCompanyId(employeeId, companyId).orElseThrow(() -> {
			LOG.warn(String.format("Cannot find the employee by id: %s for the company by id: %s", employeeId,
					companyId));
			return new EmployeeNotFoundException(NotFoundErrorEnum.EMPLOYEE_NOT_FOUND);
		});

		employeeRepo.delete(employee);

		LOG.warn(String.format("The employee by id: %s for the company by id: %s is deleted!", employeeId, companyId));
		return ResponseDTO.<String>builder().data("The employee is deleted").message("Success").status(HttpStatus.NO_CONTENT.value()).build();
	}

	@SuppressWarnings("static-access")
	@Override
	public ResponseDTO<EmployeeDTO> editEmployeePartially(Long id, EmployeeRequest employee) {
		Employee emp = employeeRepo.findById(id).orElseThrow(() -> {
			LOG.warn(String.format("Cannot find the employee by id: %s", id));
			return new EmployeeNotFoundException(NotFoundErrorEnum.EMPLOYEE_NOT_FOUND);
		});

		emp.setName(employee.getName());
		emp.setSurname(employee.getSurname());
		emp.setAddress(employee.getAddress());

		Employee updated = employeeRepo.save(emp);

		EmployeeDTO dto = new EmployeeDTO().builder().Name(updated.getName()).Surname(updated.getSurname())
				.Email(updated.getEmail()).Address(updated.getAddress()).salary(updated.getSalary())
				.companyDTO(new CompanyDTO().builder().companyName(updated.getCompany().getName()).build()).build();

		LOG.warn(String.format("The employee by id: %s is edited!", id));
		return ResponseDTO.<EmployeeDTO>builder().data(dto).message("Success").status(HttpStatus.OK.value()).build();
	}

	@SuppressWarnings("static-access")
	@Override
	public ResponseDTO<SalaryDTO> getAverageSalaryH2(Long companyId) {
		List<Employee> employees = employeeRepo.findByCompanyId(companyId);
		
		if (employees == null || employees.isEmpty()) {
			LOG.warn(String.format("The average salary is null for company id: %s", companyId));
			throw new AverageSalaryIsNullException(NotFoundErrorEnum.AVERAGE_SALARY_IS_NULL);
		}

		Double avgSalary = employees.stream().mapToDouble(e -> e.getSalary()).average().orElse(0.0);

		SalaryDTO dto = new SalaryDTO().builder().companyName(employees.get(0).getCompany().getName()).averageSalary(avgSalary).build();

		LOG.warn(String.format("Average salary for the company id: %s", companyId));
		return ResponseDTO.<SalaryDTO>builder().data(dto).message("Success").status(HttpStatus.OK.value()).build();
	}
	
	@SuppressWarnings("static-access")
	@Override
	public ResponseDTO<SalaryDTO> getAverageSalaryMySql(Long companyId) {
		Double avgSalary = employeeRepo.getAverageSalary(companyId);
		
		if (avgSalary == null) {
			LOG.warn(String.format("The average salary is null for company id: %s", companyId));
			throw new AverageSalaryIsNullException(NotFoundErrorEnum.AVERAGE_SALARY_IS_NULL);
		}

		SalaryDTO dto = new SalaryDTO().builder().companyName(companyId.toString()).averageSalary(avgSalary).build();

		LOG.warn(String.format("Average salary is %s for the company id: %s", avgSalary, companyId));
		return ResponseDTO.<SalaryDTO>builder().data(dto).message("Success").status(HttpStatus.OK.value()).build();
	}

}
