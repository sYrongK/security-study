package com.study.ant.member.reposiroty;

import com.study.ant.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    public Member findByUsername(String username) throws Exception;

}
