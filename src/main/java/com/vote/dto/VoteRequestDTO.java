package com.vote.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VoteRequestDTO {
	
	@NotNull(message="Voter ID is required")
	private Long voterId;
	
	@NotNull(message="Candidate ID is required")
	private Long candidateId;
}
