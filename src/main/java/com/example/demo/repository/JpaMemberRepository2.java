package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import com.example.demo.domain.kakaoMember;
import com.example.demo.domain.publicMember;

@Repository 
public interface JpaMemberRepository2 extends JpaRepository<publicMember, Long>, MemberRepository2 {
	Optional<publicMember> findByName(String name);
	List<publicMember> findAllByName(String name);
	void deleteById(Long id);
}

