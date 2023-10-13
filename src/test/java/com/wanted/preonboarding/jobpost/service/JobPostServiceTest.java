package com.wanted.preonboarding.jobpost.service;

import com.wanted.preonboarding.common.exception.ApplicationException;
import com.wanted.preonboarding.common.exception.ErrorCode;
import com.wanted.preonboarding.company.CompanyFixture;
import com.wanted.preonboarding.company.entity.Company;
import com.wanted.preonboarding.company.repository.CompanyRepository;
import com.wanted.preonboarding.jobpost.JobPostFixture;
import com.wanted.preonboarding.jobpost.dto.request.JobPostCreateRequest;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
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
    private JobPostCreateRequest jobPostCreateRequestWanted1;

    @BeforeEach
    void beforeEach() {
        companyWanted1 = CompanyFixture.companyWanted();
        jobPostWanted1 = JobPostFixture.jobPostWanted1();
        jobPostCreateRequestWanted1 = JobPostFixture.jobPostCreateRequestWanted1;
    }

    @DisplayName("채용 공고 생성 성공")
    @Test
    void createJobPost() {
        // given: 생성 요청 필드가 유효하고 companyId에 해당하는 회사가 존재하도록 설정
        Long companyId = jobPostCreateRequestWanted1.getCompanyId();
        given(companyRepository.findById(companyId)).willReturn(Optional.of(companyWanted1));

        // when
        jobPostService.createJobPost(jobPostCreateRequestWanted1);

        // then
        assertAll(
                () -> verify(companyRepository).findById(companyId),
                () -> verify(jobPostRepository).save(jobPostWanted1)
        );
    }

    @DisplayName("채용 공고 생성 실패 - companyId에 해당하는 회사가 존재하지 않을 때")
    @Test
    void createJobPost_Failure_CompanyNotFound() {
        // given: 생성 요청 필드는 유효하지만 companyId에 해당하는 회사가 존재하지 않도록 설정
        Long companyId = jobPostCreateRequestWanted1.getCompanyId();
        given(companyRepository.findById(companyId)).willReturn(Optional.empty());

        // when: companyId에 해당하는 회사가 존재하지 않을 때 채용 공고 생성 시도시 COMPANY_NOT_FOUND 예외 발생
        ApplicationException ex = assertThrows(ApplicationException.class, () -> {
            jobPostService.createJobPost(jobPostCreateRequestWanted1);
        });

        // then: ApplicationException의 ErrorCode가 COMPANY_NOT_FOUND 인지 검증
        assertAll(
                () -> verify(companyRepository).findById(companyId),
                () -> verify(jobPostRepository, never()).save(any()),
                () -> assertEquals(ErrorCode.COMPANY_NOT_FOUND, ex.getErrorCode())
        );
    }
}