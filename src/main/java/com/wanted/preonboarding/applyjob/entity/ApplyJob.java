package com.wanted.preonboarding.applyjob.entity;

import com.wanted.preonboarding.common.entity.BaseCreatedTimeEntity;
import com.wanted.preonboarding.jobpost.entity.JobPost;
import com.wanted.preonboarding.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplyJob extends BaseCreatedTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(nullable = false, name = "job_post_id")
    private JobPost jobPost;

    @Builder
    public ApplyJob(Member member, JobPost jobPost) {
        this.member = member;
        this.jobPost = jobPost;
    }
}
