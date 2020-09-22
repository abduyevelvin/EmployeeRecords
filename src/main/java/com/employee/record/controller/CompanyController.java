package com.employee.record.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.record.dto.CompanyDTO;
import com.employee.record.dto.ResponseDTO;
import com.employee.record.model.Company;
import com.employee.record.service.ICompanyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping("/api")
@Api("Employee Controller")
public class CompanyController {

	private ICompanyService companyService;

	@Autowired
	public CompanyController(ICompanyService companyService) {
		this.companyService = companyService;
	}

	@PostMapping("/companies")
	@ApiOperation(value = "return the created company", notes = "need provide the company data for creating")
	public ResponseDTO<CompanyDTO> createCompany(@Valid @RequestBody Company company) {

		return companyService.createCompany(company);
	}
	
	@GetMapping("/companies")
	@ApiOperation(value = "return all companies", notes = "page number or size can be provided as '?page=0&size=2'")
	public ResponseDTO<List<CompanyDTO>> getAllCompanies(Pageable pageable) {

		return companyService.getAllCompanies(pageable);
	}

	@GetMapping("/companies/{id}")
	@ApiOperation(value = "return the company by company id", notes = "need provide the company id in the path to get the company")
	public ResponseDTO<CompanyDTO> getCompany(@PathVariable("id") Long id) {

		return companyService.getCompany(id);
	}
	
	@PutMapping("/companies/{id}")
	@ApiOperation(value = "return the updated company by company id", notes = "need provide the company id in the path to edit the company")
	public ResponseDTO<CompanyDTO> editCompany(@PathVariable("id") Long id, @Valid @RequestBody Company company) {

		return companyService.editCompany(id, company);
	}
	
	@DeleteMapping("/companies/{id}")
	@ApiOperation(value = "return the string message", notes = "need provide the company id in the path to delete the company")
	public ResponseDTO<String> deleteCompany(@PathVariable("id") Long id) {

		return companyService.deleteCompany(id);
	}
}
