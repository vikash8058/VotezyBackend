package com.vote.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateProfileDTO {
    private String fullName;
    private String username;
    private String partyName;
    private String symbol;
}
