package com.wanted.preonboarding.jobpost.service;

import com.wanted.preonboarding.company.CompanyFixture;
import com.wanted.preonboarding.company.entity.Company;
import com.wanted.preonboarding.company.repository.CompanyRepository;
import com.wanted.preonboarding.jobpost.JobPostFixture;
import com.wanted.preonboarding.jobpost.dto.request.CreateJobPostRequest;
import com.wanted.preonboarding.jobpost.entity.JobPost;
import com.wanted.preonboarding.jobpost.repository.JobPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JobPostServiceTest {

    @Mock
    private JobPostRepository jobPostRepository;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private JobPostService jobPostService;

    private Company companyWanted1;
    private JobPost jobPostWanted1;
    private CreateJobPostRequest createJobPostRequestWanted1;

    @BeforeEach
    void beforeEach() {
        companyWanted1 = CompanyFixture.companyWanted();
        jobPostWanted1 = JobPostFixture.jobPostWanted1();
        createJobPostRequestWanted1 = JobPostFixture.createJobPostRequestWanted1;
    }

    @DisplayName("채용 공고 생성 성공")
    @Test
    void createJobPost() {
        // given: 생성 요청 필드가 유효하고 companyId에 해당하는 회사가 존재할 때
        Long companyId = createJobPostRequestWanted1.getCompanyId();
        given(companyRepository.findById(companyId)).willReturn(Optional.of(companyWanted1));

        // when
        jobPostService.createJobPost(createJobPostRequestWanted1);

        // then
        assertAll(
                () -> verify(companyRepository).findById(companyId),
                () -> verify(jobPostRepository).save(jobPostWanted1)
        );
    }
}