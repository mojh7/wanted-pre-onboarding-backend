package com.wanted.preonboarding.company.repository;

import com.wanted.preonboarding.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
