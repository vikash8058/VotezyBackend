package com.vote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vote.entity.Candidate;
import com.vote.entity.Vote;
import com.vote.exception.ResourceNotFoundException;
import com.vote.repository.CandidateRepo;

@Service
public class CandidateService {
	private CandidateRepo candiRepo;

	@Autowired
	public CandidateService(CandidateRepo candiRepo) {
		this.candiRepo = candiRepo;
	}
	
	//Add Candidate
	public Candidate addCandidate(Candidate candi) {
		return candiRepo.save(candi);
	}
	
	//Get Candidate
	public List<Candidate>getAllCandi(){
		return candiRepo.findAll();
	}
	
	//GetCandidateById
	public Candidate getCandidateById(Long id) {
		Candidate candi=candiRepo.findById(id).orElse(null);
		if(candi==null) {
			throw new ResourceNotFoundException("Candidate with id : "+id+" is not found");
		}
		return candi;
	}
	
	//Update
	public Candidate updateCandidate(Long id,Candidate updatedCandi) {
		Candidate candi=getCandidateById(id);
		if(candi==null) {
			throw new ResourceNotFoundException("Candidate with id : "+id+" is not found");
		}
		if(updatedCandi.getName()!=null) {
			candi.setName(updatedCandi.getName());
		}
		if(updatedCandi.getParty()!=null) {
			candi.setParty(updatedCandi.getParty());
		}
		return candiRepo.save(candi);
	}
	
	//delete
	
	public void deleteCandidate(Long id) {
		Candidate candi=getCandidateById(id);
		List<Vote> votes=candi.getVote();
		for(Vote v:votes) {
			v.setCandidate(null);
		}
		candi.getVote().clear();
		candiRepo.delete(candi);
	}
}









