package com.example.demo.repository;

import com.example.demo.domain.*;
import java.util.*;

public interface MemberRepository {
	kakaoMember save(kakaoMember member);
	
	List<kakaoMember> findAll();
	
	void deleteById(Long id);
}
