package com.example.demo.repository;

import com.example.demo.domain.*;
import java.util.*;

public interface MemberRepository2 {
	
	publicMember save(publicMember member);
	List<publicMember> findAll();
	void deleteById(Long id);
	
}
