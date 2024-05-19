package com.SBTM.TournamentManager.Controller;

import com.SBTM.TournamentManager.DTO.ShuffleRequestDto;
import com.SBTM.TournamentManager.Model.Match;
import com.SBTM.TournamentManager.Model.User;
import com.SBTM.TournamentManager.Security.CustomUserDetails;
import com.SBTM.TournamentManager.Security.CustomUserDetailsService;
import com.SBTM.TournamentManager.Service.MatchService;
import com.SBTM.TournamentManager.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matches")
public class MatchController {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    private MatchService matchService;


    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<List<Match>> getAllMatches() {
        List<Match> matches = matchService.getAllMatches();
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Match> getMatchById(@PathVariable Long id) {
        Match match = matchService.getMatchById(id);
        return ResponseEntity.ok(match);
    }

    @PostMapping("/create")
    public ResponseEntity<Match> createMatchWithShuffledTeams(@RequestBody ShuffleRequestDto shuffleRequestDto, Authentication authentication) {
        Integer organizerId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        User organizer = userService.getUserById(Long.valueOf(organizerId));
        Match match = matchService.createMatchWithShuffledTeams(shuffleRequestDto.getPlayerNames(), organizer);
        return ResponseEntity.ok(match);
    }

    @PostMapping("/{id}/reshuffle")
    public ResponseEntity<Match> reshuffleTeams(@PathVariable Long id, Authentication authentication) {
        Integer userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        Match match = matchService.getMatchById(id);
        if (match.getOrganizer().getId().equals(userId)) {
            Match reshuffledMatch = matchService.reshuffleTeams(id);
            return ResponseEntity.ok(reshuffledMatch);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @PutMapping("/{id}/winner")
    public ResponseEntity<Match> updateMatchWinner(@PathVariable Long id, @RequestParam Long winnerId, Authentication authentication) {
        Integer userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        Match match = matchService.getMatchById(id);
        if (match.getOrganizer().getId().equals(userId)) {
            Match updatedMatch = matchService.updateMatchWinner(id, winnerId);
            return ResponseEntity.ok(updatedMatch);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}