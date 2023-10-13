package com.wanted.preonboarding.jobpost;

import com.wanted.preonboarding.company.CompanyFixture;
import com.wanted.preonboarding.company.entity.Company;
import com.wanted.preonboarding.jobpost.dto.request.CreateJobPostRequest;
import com.wanted.preonboarding.jobpost.entity.JobPost;


public class JobPostFixture {

    public static CreateJobPostRequest createJobPostRequestWanted1 = new CreateJobPostRequest(
            1L, "백엔드 주니어 개발자", 1000000L,
            "Python", "원티드랩에서 백엔드 주니어 개발자를 채용합니다.");

    public static CreateJobPostRequest createJobPostRequestWanted2 = new CreateJobPostRequest(
            1L, "프론트엔드 주니어 개발자", 1000000L,
            "JavaScript", "원티드랩에서 프론트엔드 주니어 개발자를 채용합니다.");

    public static JobPost jobPostWanted1() {
        Company company = CompanyFixture.companyWanted();

        JobPost jobPost = JobPost.builder()
                .company(company)
                .position("백엔드 주니어 개발자")
                .reward(1000000L)
                .skills("Python")
                .description("원티드랩에서 백엔드 주니어 개발자를 채용합니다")
                .build();

        return jobPost;
    }
}
