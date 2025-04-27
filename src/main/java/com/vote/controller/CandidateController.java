package com.vote.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vote.entity.Candidate;
import com.vote.service.CandidateService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/candidate")
public class CandidateController {
	private CandidateService candiSerivce;

	@Autowired
	public CandidateController(CandidateService candiSerivce) {
		this.candiSerivce = candiSerivce;
	}
	
	@PostMapping("/add")
	public ResponseEntity<Candidate> addCandi(@RequestBody @Valid Candidate candi){
		Candidate savedCandi=candiSerivce.addCandidate(candi);
		return new ResponseEntity<Candidate>(savedCandi,HttpStatus.CREATED);
	}
	
	@GetMapping("/get")
	public ResponseEntity<List<Candidate>>getAllCandi(){
		List<Candidate> allCandi=candiSerivce.getAllCandi();
		return new ResponseEntity<List<Candidate>>(allCandi,HttpStatus.OK);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Candidate>getCandidatebyId(@PathVariable Long id){
		Candidate candi=candiSerivce.getCandidateById(id);
		return new ResponseEntity<Candidate>(candi,HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Candidate>updateCandi(@PathVariable Long id, @RequestBody Candidate updatedCandi){
		Candidate candi=candiSerivce.updateCandidate(id, updatedCandi);
		return new ResponseEntity<Candidate>(candi,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String>deleteCandidate(@PathVariable Long id){
		candiSerivce.deleteCandidate(id);
		return new ResponseEntity<String>("Candidate with id : "+id+" deleted successfully!!!",HttpStatus.OK);
	}
}
