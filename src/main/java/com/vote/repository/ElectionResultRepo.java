package com.vote.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vote.entity.ElectionResult;

public interface ElectionResultRepo extends JpaRepository<ElectionResult, Long>{
	Optional<ElectionResult> findByElectionName(String electionName);
}
