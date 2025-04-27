package com.vote.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vote.dto.VoteRequestDTO;
import com.vote.dto.VoteResponseDTO;
import com.vote.entity.Vote;
import com.vote.service.VoteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/votes")
@CrossOrigin
public class VotingController {
	private VoteService voteService;

	@Autowired
	public VotingController(VoteService voteService) {
		this.voteService = voteService;
	}
	
	@PostMapping("/cast")
	public ResponseEntity<VoteResponseDTO> castVote(@RequestBody @Valid VoteRequestDTO voteRequest){
		Vote vote =voteService.castVote(voteRequest.getVoterId(), voteRequest.getCandidateId());
		VoteResponseDTO voteResponse=new VoteResponseDTO("vote casted successfully",true,vote.getVoterId(),vote.getCandidateId());
		return new ResponseEntity<VoteResponseDTO>(voteResponse,HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<Vote>> getAllVotes(){
		List<Vote> allVotings=voteService.getAllVotes();
		return new ResponseEntity<>(allVotings,HttpStatus.OK);
	}
}
