package com.SBTM.TournamentManager.Controller;

import com.SBTM.TournamentManager.Model.Match;
import com.SBTM.TournamentManager.Service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private MatchService matchService;

    @GetMapping("/organized/{userId}")
    public ResponseEntity<List<Match>> getMatchesOrganizedByUser(@PathVariable Long userId) {
        List<Match> matches = matchService.getMatchesOrganizedByUser(userId);
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<Match>> getMatchesAllByUser(@PathVariable Long userId) {
        List<Match> matches = matchService.getMatchesAllByUser(userId);
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/participated/{userId}")
    public ResponseEntity<List<Match>> getMatchesParticipatedByUser(@PathVariable Long userId) {
        List<Match> matches = matchService.getMatchesParticipatedByUser(userId);
        return ResponseEntity.ok(matches);
    }

}