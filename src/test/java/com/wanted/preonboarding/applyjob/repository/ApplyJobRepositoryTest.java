package com.wanted.preonboarding.applyjob.repository;

import com.wanted.preonboarding.applyjob.entity.ApplyJob;
import com.wanted.preonboarding.common.RepositoryTest;
import com.wanted.preonboarding.company.entity.Company;
import com.wanted.preonboarding.company.repository.CompanyRepository;
import com.wanted.preonboarding.jobpost.entity.JobPost;
import com.wanted.preonboarding.jobpost.repository.JobPostRepository;
import com.wanted.preonboarding.member.entity.Member;
import com.wanted.preonboarding.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ApplyJobRepositoryTest extends RepositoryTest {

    @Autowired
    private ApplyJobRepository applyJobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Company company;
    private JobPost jobPost;
    private Member member;

    @BeforeEach
    void beforeEach() {
        company = Company.builder()
                         .name("카카오")
                         .nation("한국")
                         .region("판교")
                         .build();

        jobPost = JobPost.builder()
                         .company(company)
                         .position("프론트엔드 개발")
                         .reward(2500000L)
                         .skills("JavaScript, React")
                         .description("카카오에서 프론트엔드 개발자를 채용합니다.")
                         .build();

        member = Member.builder()
                       .name("김철수")
                       .build();
    }

    @Test
    @DisplayName("채용 공고와 사용자로 채용 공고 지원 내역을 조회할 수 있다")
    void findByJobPostAndMember() {
        // given
        companyRepository.save(company);
        jobPostRepository.save(jobPost);
        memberRepository.save(member);
        ApplyJob applyJob = ApplyJob.builder()
                                    .jobPost(jobPost)
                                    .member(member)
                                    .build();
        applyJobRepository.save(applyJob);
        entityManager.flush();

        // when
        Optional<ApplyJob> retrievedApplyJob = applyJobRepository.findByJobPostAndMember(jobPost, member);

        // then
        assertAll(
                () -> assertThat(retrievedApplyJob.get().getJobPost()).isEqualTo(jobPost),
                () -> assertThat(retrievedApplyJob.get().getMember()).isEqualTo(member)
        );
    }
}