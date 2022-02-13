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
public class MemberService_kakao {
	
	private final JpaMemberRepository memberRepository;//kakao
	
	

	public MemberService_kakao(JpaMemberRepository memberRepository) {
		
	this.memberRepository = memberRepository;
	
	}

	
	//회원가입
	public long kakaoJoin(kakaoMember member) {
		//vaildateDuplicateMember(member);
		
		memberRepository.save(member);
		return member.getId();
	}
	
	private void vaildateDuplicateMember(kakaoMember member) {
		memberRepository.findByName(member.getName()).ifPresent(m -> {
			throw new IllegalStateException("이미 존재하는 회원입니다.");//이게 문젠데!!!!!!!ㅣ싣바ㅓ사ㅣㅎㅈㄹ우ㅏㅓㅠㅇ
			
			// findbyname메소드의 반환형이 optional이기 때문에 따로 변수 정의를 하지 않고
			// ifPresent라는 optional내부 메소드를 써줄 수 있다.
			
		});
	}
	
	public List<kakaoMember> findMembers() {
		return memberRepository.findAll();
	}
		
}
