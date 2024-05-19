package com.SBTM.TournamentManager.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShuffleRequestDto {
    private List<String> playerNames;
    private Long organizerId;
}