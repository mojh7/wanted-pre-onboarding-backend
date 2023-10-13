package com.wanted.preonboarding.jobpost.service;

import com.wanted.preonboarding.common.exception.ApplicationException;
import com.wanted.preonboarding.common.exception.ErrorCode;
import com.wanted.preonboarding.company.entity.Company;
import com.wanted.preonboarding.company.repository.CompanyRepository;
import com.wanted.preonboarding.jobpost.dto.request.CreateJobPostRequest;
import com.wanted.preonboarding.jobpost.entity.JobPost;
import com.wanted.preonboarding.jobpost.repository.JobPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobPostService {
    private final JobPostRepository jobPostRepository;
    private final CompanyRepository companyRepository;

    public JobPostService(final JobPostRepository jobPostRepository, final CompanyRepository companyRepository) {
        this.jobPostRepository = jobPostRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional
    public void createJobPost(CreateJobPostRequest request) {
        Company company = companyRepository.findById(request.getCompanyId())
                                           .orElseThrow(() -> new ApplicationException(ErrorCode.COMPANY_NOT_FOUND));

        JobPost jobPost = request.toEntity(company);
        jobPostRepository.save(jobPost);
    }
}
