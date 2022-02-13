package com.example.demo.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm_kakao {//카카오맴버

	private Long id;
	private String name;
	private String personal_token;
	private String access_token;
	private String email;
}
