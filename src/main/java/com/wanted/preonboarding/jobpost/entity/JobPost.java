package com.wanted.preonboarding.jobpost.entity;

import com.wanted.preonboarding.common.entity.BaseTimeEntity;
import com.wanted.preonboarding.company.entity.Company;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Entity
@SQLDelete(sql = "UPDATE job_post SET is_deleted = true WHERE id = ?")
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobPost extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "company_id")
    private Company company;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private Long reward;

    @Column(nullable = false)
    private String skills;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean isDeleted = false;

    @Builder
    public JobPost(Company company, String position, Long reward,
                   String skills, String description) {
        this.company = company;
        this.position = position;
        this.reward = reward;
        this.skills = skills;
        this.description = description;
    }

    public JobPost update(JobPost newJobPost) {
        this.position = newJobPost.position;
        this.reward = newJobPost.reward;
        this.skills = newJobPost.skills;
        this.description = newJobPost.description;
        return this;
    }
}
