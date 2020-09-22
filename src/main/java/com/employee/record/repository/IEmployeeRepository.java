package com.employee.record.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import com.employee.record.model.Employee;

public interface IEmployeeRepository extends JpaRepository<Employee, Long> {
	Page<Employee> findByCompanyId(Long companyId, Pageable pageable);
	Optional<Employee> findByIdAndCompanyId(Long companyId, Long employeeId);
	
//	@Query("SELECT AVG(e.salary) FROM Employee e WHERE e.company = ?1")
//	Double findSalaryByCompanyId(Company companyId);
	
	List<Employee> findByCompanyId(Long companyId);
	
	@Procedure(procedureName = "GetAverageSalary")
	Double getAverageSalary(@Param("company_id") Long companyId);
	
}
