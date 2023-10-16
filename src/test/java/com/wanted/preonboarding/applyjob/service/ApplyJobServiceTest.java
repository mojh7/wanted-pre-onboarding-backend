package com.wanted.preonboarding.applyjob.service;

import com.wanted.preonboarding.applyjob.ApplyJobFixture;
import com.wanted.preonboarding.applyjob.dto.request.ApplyJobCreateRequest;
import com.wanted.preonboarding.applyjob.entity.ApplyJob;
import com.wanted.preonboarding.applyjob.repository.ApplyJobRepository;
import com.wanted.preonboarding.common.exception.ApplicationException;
import com.wanted.preonboarding.common.exception.ErrorCode;
import com.wanted.preonboarding.jobpost.JobPostFixture;
import com.wanted.preonboarding.jobpost.entity.JobPost;
import com.wanted.preonboarding.jobpost.repository.JobPostRepository;
import com.wanted.preonboarding.member.MemberFixture;
import com.wanted.preonboarding.member.entity.Member;
import com.wanted.preonboarding.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ApplyJobServiceTest {

    @Mock
    private ApplyJobRepository applyJobRepository;

    @Mock
    private JobPostRepository jobPostRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private ApplyJobService applyJobService;

    private Member memberChulSoo;
    private JobPost jobPostWanted1;
    private ApplyJob applyJob1;
    private ApplyJobCreateRequest applyJobCreateRequest1;

    @BeforeEach
    void beforeEach() {
        memberChulSoo = MemberFixture.MEMBER_CHULSOO();
        jobPostWanted1 = JobPostFixture.JOBPOST_WANTED1();
        applyJob1 = ApplyJobFixture.APPLY_JOB1();
        applyJobCreateRequest1 = ApplyJobFixture.APPLY_JOB_CREATE_REQUEST1;
    }

    @Test
    @DisplayName("채용 공고 지원 성공")
    void applyJobPost() {
        // given
        Long jobPostId = jobPostWanted1.getId();
        Long memberId = memberChulSoo.getId();
        given(jobPostRepository.findByIdAndIsDeletedFalse(jobPostId)).willReturn(Optional.of(jobPostWanted1));
        given(memberRepository.findById(memberId)).willReturn(Optional.of(memberChulSoo));
        given(applyJobRepository.findByJobPostAndMember(jobPostWanted1, memberChulSoo)).willReturn(Optional.empty());

        // when
        applyJobService.applyJobPost(applyJobCreateRequest1);

        // then
        assertAll(
                () -> verify(jobPostRepository).findByIdAndIsDeletedFalse(jobPostId),
                () -> verify(memberRepository).findById(memberId),
                () -> verify(applyJobRepository).findByJobPostAndMember(jobPostWanted1, memberChulSoo),
                () -> verify(applyJobRepository).save(any(ApplyJob.class))
        );
    }

    @Test
    @DisplayName("채용 공고 지원 실패 : 채용 공고가 존재 하지 않을 때")
    void applyJobPost_Failure_JobPostNotFound() {
        // given
        Long jobPostId = jobPostWanted1.getId();
        given(jobPostRepository.findByIdAndIsDeletedFalse(jobPostId)).willReturn(Optional.empty());

        // when
        ApplicationException ex = assertThrows(ApplicationException.class,
                () -> applyJobService.applyJobPost(applyJobCreateRequest1));

        // then
        assertAll(
                () -> verify(jobPostRepository).findByIdAndIsDeletedFalse(jobPostId),
                () -> verify(memberRepository, never()).findById(any(Long.class)),
                () -> verify(applyJobRepository, never()).findByJobPostAndMember(any(JobPost.class), any(Member.class)),
                () -> verify(applyJobRepository, never()).save(any(ApplyJob.class)),
                () -> assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.JOBPOST_NOT_FOUND)
        );
    }

    @Test
    @DisplayName("채용 공고 지원 실패 : 채용 공고는 존재하지만 사용자가 없을 때")
    void applyJobPost_Failure_MemberNotFound() {
        // given
        Long jobPostId = jobPostWanted1.getId();
        Long memberId = memberChulSoo.getId();
        given(jobPostRepository.findByIdAndIsDeletedFalse(jobPostId)).willReturn(Optional.of(jobPostWanted1));
        given(memberRepository.findById(memberId)).willReturn(Optional.empty());

        // when
        ApplicationException ex = assertThrows(ApplicationException.class,
                () -> applyJobService.applyJobPost(applyJobCreateRequest1));

        // then
        assertAll(
                () -> verify(jobPostRepository).findByIdAndIsDeletedFalse(jobPostId),
                () -> verify(memberRepository).findById(memberId),
                () -> verify(applyJobRepository, never()).findByJobPostAndMember(any(JobPost.class), any(Member.class)),
                () -> verify(applyJobRepository, never()).save(any(ApplyJob.class)),
                () -> assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.MEMBER_NOT_FOUND)
        );
    }

    @Test
    @DisplayName("채용 공고 지원 실패 : 사용자가 해당 채용 공고에 이미 지원 했을 때")
    void applyJobPost_Failure_AlreadyApplyJobPost() {
        // given
        Long jobPostId = jobPostWanted1.getId();
        Long memberId = memberChulSoo.getId();
        given(jobPostRepository.findByIdAndIsDeletedFalse(jobPostId)).willReturn(Optional.of(jobPostWanted1));
        given(memberRepository.findById(memberId)).willReturn(Optional.of(memberChulSoo));
        given(applyJobRepository.findByJobPostAndMember(jobPostWanted1, memberChulSoo)).willReturn(Optional.of(applyJob1));

        // when
        ApplicationException ex = assertThrows(ApplicationException.class,
                () -> applyJobService.applyJobPost(applyJobCreateRequest1));

        // then
        assertAll(
                () -> verify(jobPostRepository).findByIdAndIsDeletedFalse(jobPostId),
                () -> verify(memberRepository).findById(memberId),
                () -> verify(applyJobRepository).findByJobPostAndMember(jobPostWanted1, memberChulSoo),
                () -> verify(applyJobRepository, never()).save(any(ApplyJob.class)),
                () -> assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.ALREADY_APPLY_JOBPOST)
        );
    }
}