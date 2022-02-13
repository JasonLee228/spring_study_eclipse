package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.kakaoMember;
import com.example.demo.domain.publicMember;
import com.example.demo.repository.JpaMemberRepository;
import com.example.demo.repository.JpaMemberRepository2;
import com.example.demo.repository.MemberRepository;

@Transactional
public class MemberService_public {
	
	
	private final JpaMemberRepository2 memberRepository2;//public
	

	public MemberService_public(JpaMemberRepository2 memberRepository2) {
		
	
	this.memberRepository2 = memberRepository2;
	}

	
	
	public long publicJoin(publicMember member) {
		//vaildateDuplicateMember(member);
		
		memberRepository2.save(member);
		return member.getId();
	}
	
	public List<publicMember> findPublicMembers() {
		return memberRepository2.findAll();
	}
		
}
