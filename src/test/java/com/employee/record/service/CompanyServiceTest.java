package com.employee.record.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import com.employee.record.dto.CompanyDTO;
import com.employee.record.dto.ResponseDTO;
import com.employee.record.model.Company;
import com.employee.record.repository.ICompanyRepository;

@SpringBootTest
@ActiveProfiles("test")
public class CompanyServiceTest {

	private static final Logger LOG = LoggerFactory.getLogger(CompanyServiceTest.class);

	@Autowired
	private ICompanyService companyService;

	@MockBean(name = "companyRepo")
	private ICompanyRepository companyRepo;

	@SuppressWarnings("static-access")
	@Test
	public void getAllCompanyTest() {
		Long companyId = 1L;
		Company company = new Company(companyId, "Test Company");
		Optional<Company> optionalCompany = Optional.of(company);
		
		Mockito.when(companyRepo.findById(1L)).thenReturn(optionalCompany);
		
		CompanyDTO dto = new CompanyDTO().builder().companyName(company.getName()).build();

		ResponseDTO<CompanyDTO> response = ResponseDTO.<CompanyDTO>builder().data(dto).message("Success")
				.status(HttpStatus.OK.value()).build();
		
		LOG.info("The mock response: " + response);
		LOG.info("The mocked service response: " + companyService.getCompany(companyId));

		Assertions.assertEquals(response, companyService.getCompany(companyId));
	}
}
