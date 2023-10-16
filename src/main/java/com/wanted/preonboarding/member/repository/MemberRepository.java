package com.wanted.preonboarding.member.repository;

import com.wanted.preonboarding.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
