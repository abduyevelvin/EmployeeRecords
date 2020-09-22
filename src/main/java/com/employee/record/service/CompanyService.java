package com.employee.record.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.employee.record.dto.CompanyDTO;
import com.employee.record.dto.ResponseDTO;
import com.employee.record.enums.NotFoundErrorEnum;
import com.employee.record.exception.CompanyNotFoundException;
import com.employee.record.model.Company;
import com.employee.record.repository.ICompanyRepository;

@Service
public class CompanyService implements ICompanyService {

	private static final Logger LOG = LoggerFactory.getLogger(CompanyService.class);

	private ICompanyRepository companyRepo;

	@Autowired
	public CompanyService(ICompanyRepository companyRepo) {
		this.companyRepo = companyRepo;
	}

	@SuppressWarnings("static-access")
	@Override
	public ResponseDTO<CompanyDTO> createCompany(Company company) {
		Company comp = companyRepo.save(company);
		CompanyDTO dto = new CompanyDTO().builder().companyName(comp.getName()).build();

		LOG.info(String.format("The company is created with id: %s", comp.getId()));
		return ResponseDTO.<CompanyDTO>builder().data(dto).message("Success").status(HttpStatus.CREATED.value()).build();
	}

	@SuppressWarnings("static-access")
	@Override
	public ResponseDTO<List<CompanyDTO>> getAllCompanies(Pageable pageable) {
		Page<Company> companies = companyRepo.findAll(pageable);

		if (companies == null || companies.isEmpty()) {
			LOG.warn("There are no companies");
			throw new CompanyNotFoundException(NotFoundErrorEnum.COMPANIES_NOT_FOUND);
		}

		List<CompanyDTO> dtos = companies.stream().map(c -> new CompanyDTO().builder().companyName(c.getName()).build())
				.collect(Collectors.toList());

		return ResponseDTO.<List<CompanyDTO>>builder().data(dtos).message("Success").status(HttpStatus.OK.value()).build();
	}

	@SuppressWarnings("static-access")
	@Override
	public ResponseDTO<CompanyDTO> getCompany(Long id) {
		Company company = companyRepo.findById(id).orElseThrow(() -> {
			LOG.warn(String.format("Cannot find the company by id: %s", id));
			return new CompanyNotFoundException(NotFoundErrorEnum.COMPANY_NOT_FOUND);
		});

		CompanyDTO dto = new CompanyDTO().builder().companyName(company.getName()).build();

		return ResponseDTO.<CompanyDTO>builder().data(dto).message("Success").status(HttpStatus.OK.value()).build();
	}

	@SuppressWarnings("static-access")
	@Override
	public ResponseDTO<CompanyDTO> editCompany(Long id, Company company) {
		Company comp = companyRepo.findById(id).orElseThrow(() -> {
			LOG.warn(String.format("Cannot find the company by id: %s", id));
			return new CompanyNotFoundException(NotFoundErrorEnum.COMPANY_NOT_FOUND);
		});

		comp.setName(company.getName());

		Company updated = companyRepo.save(comp);

		CompanyDTO dto = new CompanyDTO().builder().companyName(updated.getName()).build();

		LOG.warn(String.format("The company with id: %s is edited!", id));
		return ResponseDTO.<CompanyDTO>builder().data(dto).message("Success").status(HttpStatus.OK.value()).build();
	}

	@Override
	public ResponseDTO<String> deleteCompany(Long id) {
		Company company = companyRepo.findById(id).orElseThrow(() -> {
			LOG.warn(String.format("Cannot find the company by id: %s", id));
			return new CompanyNotFoundException(NotFoundErrorEnum.COMPANY_NOT_FOUND);
		});

		companyRepo.delete(company);

		LOG.warn(String.format("The company with id: %s is deleted!", id));
		return ResponseDTO.<String>builder().data("The company is deleted").status(HttpStatus.NO_CONTENT.value()).message("Success").build();
	}

}
