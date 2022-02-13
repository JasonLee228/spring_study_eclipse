package com.example.demo.domain;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity 
@Table(name = "Kakao_Member_List") 
@ToString 
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class kakaoMember {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 적용한 것. 
	private Long id; 
	
	@Column(length = 200, nullable = false)
	private String name;
	
	@Column(length = 200, nullable = false)
	private String personal_token;
	
	@Column(length = 200, nullable = false)
	private String access_token;
	
	@Column(length = 200, nullable = true)
	private String email;

}
