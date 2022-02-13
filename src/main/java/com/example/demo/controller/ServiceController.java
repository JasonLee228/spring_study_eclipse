package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.kakaoMember;
import com.example.demo.domain.publicMember;
import com.example.demo.service.KakaoService;
import com.example.demo.service.MemberService_kakao;
import com.example.demo.service.MemberService_public;
import com.fasterxml.jackson.annotation.JsonInclude;

@Controller
public class ServiceController {
	
	private final KakaoService kakaoService;
    private final MemberService_kakao memberService;
    private final MemberService_public memberService2;

    @Autowired
    public ServiceController(KakaoService kakaoService, MemberService_kakao memberService, MemberService_public memberService2) {
		super();
		this.kakaoService = kakaoService;
		this.memberService = memberService;
		this.memberService2 = memberService2;
	}
    
    @RequestMapping("/login")//카카오에서 로그인을 하면 여기로 인가코드가 넘어온다. 이를 통해 나머지 토큰 및 정보를 받아오는 것.
    public String kakaoLogin(@RequestParam(value = "code", required = false) String code) throws Exception{
        System.out.println("#########" + code);
        String access_Token = kakaoService.getAccessToken(code);
        
        HashMap<String, Object> userInfo = kakaoService.getUserInfo(access_Token);
        System.out.println("###access_Token#### : " + access_Token);
        System.out.println("###userInfo#### : " + userInfo.get("email"));
        System.out.println("###nickname#### : " + userInfo.get("nickname"));
        System.out.println("###profile_image#### : " + userInfo.get("profile_image"));
        
        kakaoMember member = new kakaoMember();
        member.setName((String) userInfo.get("nickname"));//저장되어있는 name을 getname을 통해 꺼내고,
		member.setPersonal_token((String)code);
		member.setAccess_token((String)access_Token);
		member.setEmail((String)userInfo.get("email"));
		memberService.kakaoJoin(member);//회원가입 실행
        //return "kakaoPage";
		return "redirect:/";
    }
    
    @RequestMapping("/logout")//카카오 로그아웃
    public String kakaoLogout(@RequestParam(value = "code", required = false) String code) throws Exception{
        System.out.println("#########" + code);
       
        //return "kakaoPage";
		return "redirect:/";
    }
    
    

	//회원가입
	@GetMapping(value = "/members/new")
	public String createForm() {
		return "createMemberForm";
	}
	
	@PostMapping(value = "/members/new")//해당 경로에서 일어날 일. html코드의 method="post"라는 내용을 타고 들어와 @postMapping이 실행된다.
	public String create(MemberForm_public form) {
		publicMember member = new publicMember();
		member.setName(form.getName());//저장되어있는 name을 getname을 통해 꺼내고,
		member.setEmail(form.getEmail());
		memberService2.publicJoin(member);//회원가입 실행
		return "redirect:/";//초기화면, 최상위 경로인 /로 돌아간다.
	}
	
	
	//전체 맴버 리스트(kakao)
	@GetMapping("/members/kakao")
	public String memberlist_kakao(Model model) {
		List<kakaoMember> members = memberService.findMembers();
		model.addAttribute("members", members);
		System.out.println(members);
		return "kakaoMemberList";
	}
	
	//전체 맴버 리스트(public)
	@GetMapping("/members/public")
	public String memberlist_public(Model model) {
		List<publicMember> members_public = memberService2.findPublicMembers();
		model.addAttribute("members", members_public);
		System.out.println(members_public);
		return "publicMemberList";	
	}
	
}