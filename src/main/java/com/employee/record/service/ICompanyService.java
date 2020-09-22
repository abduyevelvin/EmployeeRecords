package com.employee.record.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.employee.record.dto.CompanyDTO;
import com.employee.record.dto.ResponseDTO;
import com.employee.record.model.Company;

public interface ICompanyService {

	ResponseDTO<CompanyDTO> createCompany(Company company);
	ResponseDTO<List<CompanyDTO>> getAllCompanies(Pageable pageable);
	ResponseDTO<CompanyDTO> getCompany(Long id);
	ResponseDTO<CompanyDTO> editCompany(Long id, Company company);
	ResponseDTO<String> deleteCompany(Long id);
}
