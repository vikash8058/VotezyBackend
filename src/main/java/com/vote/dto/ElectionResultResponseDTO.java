package com.vote.dto;

import lombok.Data;

@Data
public class ElectionResultResponseDTO {
	
	private String electionName;
	private int totalVotes;
	private long winnerId;
	private int winnerVotes;

}
