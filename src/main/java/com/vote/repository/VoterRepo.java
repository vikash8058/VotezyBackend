package com.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vote.entity.Voter;


public interface VoterRepo extends JpaRepository<Voter, Long> {
	boolean existsByEmail(String email);
}
