package com.wanted.preonboarding.jobpost.dto.response;

import com.wanted.preonboarding.company.entity.Company;
import com.wanted.preonboarding.jobpost.entity.JobPost;
import lombok.Getter;
import java.util.List;

@Getter
public class JobPostDetailResponse {

    private Long jobPostId;

    private String companyName;

    private String nation;

    private String region;

    private String position;

    private Long reward;

    private String skills;

    private String description;

    private List<Long> companyOtherJobPostList;

    private JobPostDetailResponse(Long jobPostId, String companyName, String nation, String region, String position,
                                  Long reward, String skills, String description, List<Long> companyOtherJobPostList) {
        this.jobPostId = jobPostId;
        this.companyName = companyName;
        this.nation = nation;
        this.region = region;
        this.position = position;
        this.reward = reward;
        this.skills = skills;
        this.description = description;
        this.companyOtherJobPostList = companyOtherJobPostList;
    }

    public static JobPostDetailResponse of(final JobPost jobPost, List<Long> companyOtherJobPostList) {
        Company company = jobPost.getCompany();
        return new JobPostDetailResponse(jobPost.getId(), company.getName(), company.getNation(),
                company.getRegion(), jobPost.getPosition(), jobPost.getReward(),
                jobPost.getSkills(), jobPost.getDescription(), companyOtherJobPostList);
    }
}