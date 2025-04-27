package com.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vote.entity.Vote;

public interface VoteRepo extends JpaRepository<Vote, Long>{
	
}
