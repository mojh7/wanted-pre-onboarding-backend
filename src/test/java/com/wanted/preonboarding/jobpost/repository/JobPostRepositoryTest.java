package com.wanted.preonboarding.jobpost.repository;

import com.wanted.preonboarding.common.RepositoryTest;
import com.wanted.preonboarding.company.entity.Company;
import com.wanted.preonboarding.company.repository.CompanyRepository;
import com.wanted.preonboarding.jobpost.entity.JobPost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


public class JobPostRepositoryTest extends RepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Company company;
    private JobPost jobPost;

    @BeforeEach
    void beforeEach() {
        company = Company.builder()
                         .name("카카오")
                         .nation("한국")
                         .region("경기도")
                         .build();

        jobPost = JobPost.builder()
                         .company(company)
                         .position("프론트엔드 개발")
                         .reward(2500000L)
                         .skills("JavaScript, React")
                         .description("카카오에서 프론트엔드 개발자를 채용합니다.")
                         .build();
    }

    @Test
    @DisplayName("채용 공고를 삭제하면 isDeleted가 true로 바뀌어야 한다 ")
    void softDeleteJobPostTest() {
        // given: 삭제되지 않은 채용 공고의 isDeleted 값은 false
        companyRepository.save(company);
        jobPostRepository.save(jobPost);
        assertThat(jobPost.isDeleted()).isEqualTo(false);

        // when: 채용 공고 삭제 후 findById로 조회해보면
        jobPostRepository.delete(jobPost);
        entityManager.flush();
        Optional<JobPost> deletedJobPost = jobPostRepository.findById(jobPost.getId());

        // then: 삭제된 채용 공고의 isDeleted는 true로 바뀌어야 한다
        assertAll(
                () -> assertThat(deletedJobPost.get()).isEqualTo(jobPost),
                () -> assertThat(deletedJobPost.get()
                                               .isDeleted()).isEqualTo(true)
        );
    }
}
