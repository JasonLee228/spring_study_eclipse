package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import com.example.demo.domain.kakaoMember;
import com.example.demo.domain.publicMember;

@Repository 
public interface JpaMemberRepository extends JpaRepository<kakaoMember, Long>, MemberRepository {
	
	Optional<kakaoMember> findByName(String name);
	List<kakaoMember> findAllByName(String name);
	void deleteById(Long id);
}

