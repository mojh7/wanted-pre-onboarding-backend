package com.wanted.preonboarding.company.entity;

import com.wanted.preonboarding.common.entity.BaseTimeEntity;
import com.wanted.preonboarding.jobpost.entity.JobPost;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nation;

    @Column(nullable = false)
    private String region;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<JobPost> jobPostList = new ArrayList<>();

    @Builder
    public Company(String name, String nation, String region) {
        this.name = name;
        this.nation = nation;
        this.region = region;
    }
}
