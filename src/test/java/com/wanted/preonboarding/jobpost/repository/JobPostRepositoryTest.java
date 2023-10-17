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

import java.util.List;
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
    private JobPost jobPost2;
    private JobPost jobPost3;
    private JobPost deletedJobPost;

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

        jobPost2 = JobPost.builder()
                          .company(company)
                          .position("백엔드 개발")
                          .reward(2500000L)
                          .skills("node.js")
                          .description("카카오에서 백엔드 개발자를 채용합니다.")
                          .build();

        jobPost3 = JobPost.builder()
                          .company(company)
                          .position("데브옵스 엔지니어")
                          .reward(2500000L)
                          .skills("k8s, istio")
                          .description("카카오에서 데브옵스 엔지니어를 채용합니다.")
                          .build();

        deletedJobPost = JobPost.builder()
                                .company(company)
                                .position("백엔드")
                                .reward(500000L)
                                .skills("Java, Spring")
                                .description("카카오에서 Java&Spring 백엔드 개발자를 채용합니다.")
                                .build();
    }

    @Test
    @DisplayName("채용 공고를 삭제하면 isDeleted가 true로 바뀌어야 한다")
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

    @Test
    @DisplayName("존재하는 채용 공고 단건 조회 함수는 삭제되지 않은 채용 공고를 조회한다")
    void findByIdAndIsDeletedFalse() {
        // given
        companyRepository.save(company);
        jobPostRepository.save(jobPost);

        // when: 채용 공고를 삭제 후 findByIdAndIsDeletedFalse로 조회하면
        jobPostRepository.delete(jobPost);
        jobPostRepository.findByIdAndIsDeletedFalse(jobPost.getId());
        entityManager.flush();
        Optional<JobPost> result = jobPostRepository.findByIdAndIsDeletedFalse(jobPost.getId());

        // then: 조회되지 않아야 한다
        assertThat(result.isEmpty()).isEqualTo(true);
    }

    @Test
    @DisplayName("존재하는 채용 공고 목록 조회")
    void findAllByIsDeletedFalse() {
        // given
        companyRepository.save(company);
        jobPostRepository.save(jobPost);
        jobPostRepository.save(jobPost2);
        jobPostRepository.save(jobPost3);
        entityManager.flush();

        // when
        List<JobPost> jobPostResponseList = jobPostRepository.findAllByIsDeletedFalse();

        // then: 조회되지 않아야 한다
        assertAll(
                () -> assertThat(jobPostResponseList.size()).isEqualTo(3),
                () -> assertThat(jobPostResponseList.get(1)).isEqualTo(jobPost2)
        );
    }







    @Test
    @DisplayName("존재하는 채용 공고 목록 조회에서 삭제된 채용 공고는 조회되지 않아야 한다")
    void findAllByIsDeletedFalse_Empty() {
        // given
        companyRepository.save(company);
        jobPostRepository.save(deletedJobPost);
        jobPostRepository.delete(deletedJobPost);
        entityManager.flush();

        // when
        List<JobPost> jobPostResponseList = jobPostRepository.findAllByIsDeletedFalse();

        // then: 조회되지 않아야 한다
        assertAll(
                () -> assertThat(jobPostResponseList.isEmpty()).isTrue()
        );
    }
}
