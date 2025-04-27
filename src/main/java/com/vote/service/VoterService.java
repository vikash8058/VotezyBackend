package com.vote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vote.entity.Candidate;
import com.vote.entity.Vote;
import com.vote.entity.Voter;
import com.vote.exception.DuplicateResourceException;
import com.vote.exception.ResourceNotFoundException;
import com.vote.repository.CandidateRepo;
import com.vote.repository.VoterRepo;

import jakarta.transaction.Transactional;


@Service
public class VoterService {
	private VoterRepo voterRepo;
	private CandidateRepo candiRepo;

	@Autowired	
	public VoterService(VoterRepo voteRepo, CandidateRepo candiRepo) {
		this.voterRepo = voteRepo;
		this.candiRepo = candiRepo;
	}
	
	//AddVoter
	public Voter registerVoter(Voter voter) {
		if(voterRepo.existsByEmail(voter.getEmail())) {
			throw new DuplicateResourceException("Voter with this email id : "+voter.getEmail()+" is already exist");
		}
		return voterRepo.save(voter);
	}
	
	//GetAllVoter
	public List<Voter> getAllVoters(){
		return voterRepo.findAll();
	}
	
	//getVoterById
	public Voter getVoterById(Long id) {
		Voter voter=voterRepo.findById(id).orElse(null);
		if(voter==null) {
			throw new ResourceNotFoundException("voter with id: "+id+" not found");
		}
		return voter;
	}
	
	//update voter
	public Voter updateVoter(Long id,Voter updatedVoter) {
		Voter voter=voterRepo.findById(id).orElse(null);
		if(voter==null) {
			throw new ResourceNotFoundException("voter with id : "+id+" not found...!!!!");
		}
		if(updatedVoter.getName()!=null) {
			voter.setName(updatedVoter.getName());
		}
		if(updatedVoter.getEmail()!=null) {
			voter.setEmail(updatedVoter.getEmail());
		}
		
		return voterRepo.save(voter);
	}
	
	
	//Delete voter
	@Transactional
	public void deleteVoter(Long id) {
		Voter voter=voterRepo.findById(id).orElse(null);
		if(voter==null) {
			throw new ResourceNotFoundException("voter with id : "+id+" already not exists...!!!!");
		}
		Vote vote=voter.getVote();
		if(vote!=null) {
			Candidate cand=vote.getCandidate();
			cand.setVoteCount(cand.getVoteCount()-1);
			candiRepo.save(cand);
		}
		
		voterRepo.delete(voter);
	}
	
}
