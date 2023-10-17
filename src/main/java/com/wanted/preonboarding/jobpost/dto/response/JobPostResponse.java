package com.wanted.preonboarding.jobpost.dto.response;

import com.wanted.preonboarding.company.entity.Company;
import com.wanted.preonboarding.jobpost.entity.JobPost;
import lombok.Getter;

@Getter
public class JobPostResponse {

    private Long jobPostId;

    private String name;

    private String nation;

    private String region;

    private String position;

    private Long reward;

    private String skills;

    private JobPostResponse(Long jobPostId, String name, String nation, String region,
                           String position, Long reward, String skills) {
        this.jobPostId = jobPostId;
        this.name = name;
        this.nation = nation;
        this.region = region;
        this.position = position;
        this.reward = reward;
        this.skills = skills;
    }

    public static JobPostResponse from(final JobPost jobPost) {
        Company company = jobPost.getCompany();
        return new JobPostResponse(jobPost.getId(), company.getName(), company.getNation(),
                company.getRegion(), jobPost.getPosition(), jobPost.getReward(), jobPost.getSkills());
    }
}
