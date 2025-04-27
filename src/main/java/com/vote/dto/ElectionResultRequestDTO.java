package com.vote.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ElectionResultRequestDTO {

	@NotBlank(message="Election Name is required")
	private String electionName;
}
