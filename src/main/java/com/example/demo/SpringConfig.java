package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.repository.JpaMemberRepository;
import com.example.demo.repository.JpaMemberRepository2;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.KakaoService;
import com.example.demo.service.MemberService_kakao;
import com.example.demo.service.MemberService_public;

@Configuration
public class SpringConfig {
	
	//private final MemberRepository memberRepository;
	
	private final JpaMemberRepository memberRepository;
	private final JpaMemberRepository2 memberRepository2;
	
	public SpringConfig(JpaMemberRepository memberRepository,JpaMemberRepository2 memberRepository2) {
		this.memberRepository = memberRepository;
		this.memberRepository2 = memberRepository2;
	}
	
	@Bean
	 public MemberService_kakao memberService() {
	 return new MemberService_kakao(memberRepository);
	 }
	
	@Bean
	public MemberService_public memberService2() {
		return new MemberService_public(memberRepository2);
	}
	
	/*
	@Bean
	public MemberRepository memberRepository() {
		return new JpaMemberRepository(em);
	}*/
	
}
