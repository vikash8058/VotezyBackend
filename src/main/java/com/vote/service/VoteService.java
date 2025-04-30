package com.vote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vote.entity.Candidate;
import com.vote.entity.Vote;
import com.vote.entity.Voter;
import com.vote.exception.ElectionResultAlreadyDeclaredException;
import com.vote.exception.ResourceNotFoundException;
import com.vote.exception.VoteNotAllowedException;
import com.vote.repository.CandidateRepo;
import com.vote.repository.ElectionResultRepo;
import com.vote.repository.VoteRepo;
import com.vote.repository.VoterRepo;

import jakarta.transaction.Transactional;

@Service
public class VoteService {

	private VoteRepo voteRepo;
	private VoterRepo voterRepo;
	private CandidateRepo candiRepo;
	private ElectionResultRepo electionRepo;
	
	@Autowired
	public VoteService(VoteRepo voteRepo, VoterRepo voterRepo, CandidateRepo candiRepo,ElectionResultRepo electionRepo) {
		this.voteRepo = voteRepo;
		this.voterRepo = voterRepo;
		this.candiRepo = candiRepo;
		this.electionRepo=electionRepo;
	}
	
	@Transactional
	public Vote castVote(Long voterId, Long candidateId ) {
		if(electionRepo.count()==1) {
			throw new ElectionResultAlreadyDeclaredException("Voter can not cast vote because Election Result has been already declared");
		}
		
		if(!voterRepo.existsById(voterId)) {
			throw new ResourceNotFoundException("Voter not found with this ID : "+voterId);
		}
		if(!candiRepo.existsById(candidateId)) {
			throw new ResourceNotFoundException("Candidate not found with this ID : "+candidateId);
		}
		Voter voter=voterRepo.findById(voterId).get();
		
		if(voter.isHasVoted()) {
			throw new VoteNotAllowedException("Voter ID : "+voterId+" has already casted vote");
		}
		Candidate candi=candiRepo.findById(candidateId).get();
		Vote vote=new Vote();
		vote.setVoter(voter);
		vote.setCandidate(candi);
		
		voteRepo.save(vote);
		
		candi.setVoteCount(candi.getVoteCount()+1);
		candiRepo.save(candi);
		voter.setHasVoted(true);
		voterRepo.save(voter);
		
		return vote;
	}
	 
	public List<Vote> getAllVotes(){
		return voteRepo.findAll();
	}
	
}
