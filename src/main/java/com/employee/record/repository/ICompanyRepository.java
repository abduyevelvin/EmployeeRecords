package com.employee.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.record.model.Company;

public interface ICompanyRepository extends JpaRepository<Company, Long> {
	
}
