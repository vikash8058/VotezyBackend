package com.vote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vote.entity.Candidate;

public interface CandidateRepo extends JpaRepository<Candidate, Long>{
	List<Candidate> findAllByOrderByVoteCountDesc();
}
