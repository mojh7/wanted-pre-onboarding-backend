package com.wanted.preonboarding.jobpost.service;

import com.wanted.preonboarding.common.exception.ApplicationException;
import com.wanted.preonboarding.common.exception.ErrorCode;
import com.wanted.preonboarding.company.CompanyFixture;
import com.wanted.preonboarding.company.entity.Company;
import com.wanted.preonboarding.company.repository.CompanyRepository;
import com.wanted.preonboarding.jobpost.JobPostFixture;
import com.wanted.preonboarding.jobpost.dto.request.JobPostCreateRequest;
import com.wanted.preonboarding.jobpost.dto.request.JobPostUpdateRequest;
import com.wanted.preonboarding.jobpost.dto.response.JobPostResponse;
import com.wanted.preonboarding.jobpost.entity.JobPost;
import com.wanted.preonboarding.jobpost.repository.JobPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
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
    private JobPost jobPostWanted2;
    private JobPost jobPostNaver1;
    private JobPost deletedJobPostNaver2;
    private JobPostCreateRequest jobPostCreateRequestWanted1;
    private JobPostUpdateRequest jobPostUpdateRequestWanted1;
    private JobPostUpdateRequest jobPostUpdateRequestNaver1;

    @BeforeEach
    void beforeEach() {
        companyWanted1 = CompanyFixture.COMPANY_WANTED();

        jobPostWanted1 = JobPostFixture.JOBPOST_WANTED1();
        jobPostWanted2 = JobPostFixture.JOBPOST_WANTED2();
        jobPostNaver1 = JobPostFixture.JOBPOST_NAVER1();
        deletedJobPostNaver2 = JobPostFixture.DELETED_JOBPOST_NAVER2();

        jobPostCreateRequestWanted1 = JobPostFixture.JOBPOST_CREATE_REQUEST_WANTED1;

        jobPostUpdateRequestWanted1 = JobPostFixture.JOBPOST_UPDATE_REQUEST_WANTED1;
        jobPostUpdateRequestNaver1 = JobPostFixture.JOBPOST_UPDATE_REQUEST_NAVER1;
    }

    @Test
    @DisplayName("채용 공고 생성 성공")
    void createJobPost() {
        // given: 생성 요청 필드가 유효하고 companyId에 해당하는 회사가 존재하도록 설정
        Long companyId = jobPostCreateRequestWanted1.getCompanyId();
        given(companyRepository.findById(companyId)).willReturn(Optional.of(companyWanted1));

        // when
        jobPostService.createJobPost(jobPostCreateRequestWanted1);

        // then
        assertAll(
                () -> verify(companyRepository).findById(companyId),
                () -> verify(jobPostRepository).save(any(JobPost.class))
        );
    }

    @Test
    @DisplayName("채용 공고 생성 실패 : companyId에 해당하는 회사가 존재하지 않을 때")
    void createJobPost_Failure_CompanyNotFound() {
        // given: 생성 요청 필드는 유효하지만 companyId에 해당하는 회사가 존재하지 않도록 설정
        Long companyId = jobPostCreateRequestWanted1.getCompanyId();
        given(companyRepository.findById(companyId)).willReturn(Optional.empty());

        // when: companyId에 해당하는 회사가 존재하지 않을 때 채용 공고 생성 시도
        ApplicationException ex = assertThrows(ApplicationException.class, () -> {
            jobPostService.createJobPost(jobPostCreateRequestWanted1);
        });

        // then: ErrorCode = COMPANY_NOT_FOUND
        assertAll(
                () -> verify(companyRepository).findById(companyId),
                () -> verify(jobPostRepository, never()).save(any(JobPost.class)),
                () -> assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.COMPANY_NOT_FOUND)
        );
    }

    @Test
    @DisplayName("채용 공고 목록 조회 성공")
    void retrieveJobPostList() {
        // given
        List<JobPost> jobPostList = new ArrayList<>();
        jobPostList.add(jobPostWanted1);
        jobPostList.add(jobPostNaver1);
        jobPostList.add(jobPostWanted2);
        given(jobPostRepository.findAllByIsDeletedFalse()).willReturn(jobPostList);

        // when
        List<JobPostResponse> result = jobPostService.retrieveJobPostList();

        // then
        assertAll(
                () -> verify(jobPostRepository).findAllByIsDeletedFalse(),
                () -> assertThat(result.size()).isEqualTo(jobPostList.size()),
                () -> {
                    JobPostResponse jobPostResponse = result.get(1);
                    assertThat(jobPostResponse.getJobPostId()).isEqualTo(jobPostNaver1.getId());
                    assertThat(jobPostResponse.getName()).isEqualTo(jobPostNaver1.getCompany().getName());
                    assertThat(jobPostResponse.getPosition()).isEqualTo(jobPostNaver1.getPosition());
                }
        );
    }

    @Test
    @DisplayName("채용 공고 목록 조회 성공 : 없으면 빈 List 반환")
    void retrieveJobPostList_Empty() {
        // given
        List<JobPost> jobPostList = new ArrayList<>();
        given(jobPostRepository.findAllByIsDeletedFalse()).willReturn(jobPostList);

        // when
        List<JobPostResponse> result = jobPostService.retrieveJobPostList();

        // then
        assertAll(
                () -> verify(jobPostRepository).findAllByIsDeletedFalse(),
                () -> assertThat(result.isEmpty()).isTrue()
        );
    }

    @Test
    @DisplayName("채용 공고 수정 성공")
    void updateJobPost() {
        // given: 수정 요청에서 companyId는 변경되지 않고 그 외 필드는 유효하도록 설정
        Long jobPostId = jobPostWanted1.getId();
        given(jobPostRepository.findByIdAndIsDeletedFalse(jobPostId)).willReturn(Optional.of(jobPostWanted1));

        // when: 수정 요청 필드가 유효하며 삭제되지 않고 존재하는 채용 공고 수정 시도
        jobPostService.updateJobPost(jobPostUpdateRequestWanted1, jobPostId);

        // then
        assertAll(
                () -> verify(jobPostRepository).findByIdAndIsDeletedFalse(jobPostId),
                () -> verify(jobPostRepository).save(jobPostWanted1)
        );
    }

    @Test
    @DisplayName("채용 공고 수정 실패 : 수정하려는 채용 공고가 없을 때")
    void updateJobPost_Failure_AlreadyDeletedJobpost() {
        // given: 수정 요청에서 companyId는 변경되지 않고 그 외 필드는 유효하지만 존재하지 않은 채용 공고 id 설정
        Long jobPostId = 357523L;
        given(jobPostRepository.findByIdAndIsDeletedFalse(jobPostId)).willReturn(Optional.empty());

        // when: 수정 요청 필드가 유효하지만 존재하지 않은 채용 공고 수정 시도
        ApplicationException ex = assertThrows(ApplicationException.class,
                () -> jobPostService.updateJobPost(jobPostUpdateRequestWanted1, jobPostId));

        // then: ErrorCode = JOBPOST_NOT_FOUND
        assertAll(
                () -> verify(jobPostRepository).findByIdAndIsDeletedFalse(jobPostId),
                () -> verify(jobPostRepository, never()).save(any(JobPost.class)),
                () -> assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.JOBPOST_NOT_FOUND)
        );
    }

    @Test
    @DisplayName("채용 공고 수정 실패 : 채용 공고의 company_id 필드를 수정하려 할 때")
    void updateJobPost_Failure_UnableToUpdateFields() {
        // given: 채용 공고의 companyId와 다른 companyId를 가진 수정 요청 설정
        JobPost jobPost = jobPostWanted1;
        Long jobPostId = jobPost.getId();
        JobPostUpdateRequest request = jobPostUpdateRequestNaver1;
        given(jobPostRepository.findByIdAndIsDeletedFalse(jobPostId)).willReturn(Optional.of(jobPost));

        // when
        ApplicationException ex = assertThrows(ApplicationException.class,
                () -> jobPostService.updateJobPost(request, jobPostId));

        // then: ErrorCode = UNABLE_TO_UPDATE_FIELDS
        assertAll(
                () -> assertThat(jobPost.getCompany().getId()).isNotEqualTo(request.getCompanyId()),
                () -> verify(jobPostRepository).findByIdAndIsDeletedFalse(jobPostId),
                () -> verify(jobPostRepository, never()).save(any(JobPost.class)),
                () -> assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.UNABLE_TO_UPDATE_FIELDS)
        );
    }

    @Test
    @DisplayName("채용 공고 삭제 성공")
    void deleteJobPost() {
        // given
        Long jobPostId = jobPostNaver1.getId();
        given(jobPostRepository.findByIdAndIsDeletedFalse(jobPostId)).willReturn(Optional.of(jobPostNaver1));

        // when
        jobPostService.deleteJobPost(jobPostId);

        // then
        assertAll(
                () -> verify(jobPostRepository).findByIdAndIsDeletedFalse(jobPostId),
                () -> verify(jobPostRepository).delete(jobPostNaver1)
        );
    }

    @Test
    @DisplayName("채용 공고 삭제 실패 : 채용 공고가 존재하지 않을 때")
    void deleteJobPost_Failure_JobPostNotFound() {
        // given
        Long jobPostId = 234234L;
        given(jobPostRepository.findByIdAndIsDeletedFalse(jobPostId)).willReturn(Optional.empty());

        // when
        ApplicationException ex = assertThrows(ApplicationException.class,
                () -> jobPostService.deleteJobPost(jobPostId));

        // then
        assertAll(
                () -> verify(jobPostRepository).findByIdAndIsDeletedFalse(jobPostId),
                () -> verify(jobPostRepository, never()).delete(any(JobPost.class)),
                () -> assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.JOBPOST_NOT_FOUND)
        );
    }
}