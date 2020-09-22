package com.employee.record.config;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.employee.record.repository.ICompanyRepository;
import com.employee.record.repository.IEmployeeRepository;


@Configuration
@Profile("test")
public class ConfigTest {

	@Bean
	@Qualifier("companyRepo")
	@Primary
	public ICompanyRepository companyRepo() {
		return Mockito.mock(ICompanyRepository.class);
	}
	
	@Bean
	@Qualifier("employeeRepo")
	@Primary
	public IEmployeeRepository employeeRepo() {
		return Mockito.mock(IEmployeeRepository.class);
	}
}
