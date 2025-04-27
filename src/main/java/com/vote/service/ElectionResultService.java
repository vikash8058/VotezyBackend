package com.vote.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vote.entity.Candidate;
import com.vote.entity.ElectionResult;
import com.vote.exception.ResourceNotFoundException;
import com.vote.repository.CandidateRepo;
import com.vote.repository.ElectionResultRepo;
import com.vote.repository.VoteRepo;

@Service
public class ElectionResultService {
	private CandidateRepo candiRepo;
	private ElectionResultRepo electionRepo;
	private VoteRepo voteRepo;
	
	@Autowired
	public ElectionResultService(CandidateRepo candiRepo, ElectionResultRepo electionRepo, VoteRepo voteRepo) {
		this.candiRepo = candiRepo;
		this.electionRepo = electionRepo;
		this.voteRepo = voteRepo;
	}
	
	public ElectionResult declareElectionResult(String electionName) {
		
		Optional<ElectionResult> existingResult=this.electionRepo.findByElectionName(electionName);
		if(existingResult.isPresent()) {
			return existingResult.get();
		}
		
		if(electionRepo.count()==1) {
			throw new ResourceNotFoundException("Election Result was already declared with electionName : "+electionRepo.getById((long)1).getElectionName());
		}
		
		
		if(voteRepo.count()==0) {
			throw new IllegalStateException("Cannot declared the result as no votes has been casted");
		}
		
		List<Candidate> allCandidate=candiRepo.findAllByOrderByVoteCountDesc();
		if(allCandidate.isEmpty()) {
			throw new ResourceNotFoundException("No candidate available");
		}
		
		Candidate winner=allCandidate.get(0);
		int totalVotes=0;
		for(Candidate c: allCandidate) {
			totalVotes+=c.getVoteCount();
		}
		
		ElectionResult result=new ElectionResult();
		result.setElectionName(electionName);
		result.setWinner(winner);
		result.setTotalVotes(totalVotes);
		return electionRepo.save(result);
		
	}
	
		public List<ElectionResult>getAllResults(){
			return electionRepo.findAll();
		}
}
