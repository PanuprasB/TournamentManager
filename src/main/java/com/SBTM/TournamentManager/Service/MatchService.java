package com.SBTM.TournamentManager.Service;

import com.SBTM.TournamentManager.Model.Match;
import com.SBTM.TournamentManager.Model.Player;
import com.SBTM.TournamentManager.Model.Team;
import com.SBTM.TournamentManager.Model.User;
import com.SBTM.TournamentManager.Repos.MatchRepos;
import com.SBTM.TournamentManager.Repos.PlayerRepos;
import com.SBTM.TournamentManager.Repos.TeamRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MatchService {

    @Autowired
    private MatchRepos matchRepository;

    @Autowired
    private TeamRepos teamRepository;

    @Autowired
    private PlayerRepos playerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TeamService teamService;

    public Match saveMatch(Match match) {
        return matchRepository.save(match);
    }

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public Match getMatchById(Long id) {
        return matchRepository.findById(id).orElseThrow(() -> new RuntimeException("Match not found"));
    }

    public List<Match> getMatchesOrganizedByUser(Long userId) {
        return matchRepository.findByOrganizerId(userId);
    }

    public List<Match> getMatchesAllByUser(Long userId) {
        return matchRepository.findMatchHistoryByUserId(userId);
    }

    public List<Match> getMatchesParticipatedByUser(Long userId) {
        return matchRepository.findMatchesParticipatedByUserId(userId);
    }

    public Match createMatchWithShuffledTeams(List<String> playerNames, User organizer) {
        Collections.shuffle(playerNames);
        Team team1 = new Team();
        team1.setName("Team 1");
        Team team2 = new Team();
        team2.setName("Team 2");

        team1 = teamRepository.save(team1);
        team2 = teamRepository.save(team2);

        List<Player> team1Players = new ArrayList<>();
        List<Player> team2Players = new ArrayList<>();

        for (String playerName : playerNames) {
            Player player = new Player();
            player.setName(playerName);

            User user = userService.getUserByUsername(playerName);
            if (user != null) {
                player.setUser(user);
            }

            if (team1Players.size() < playerNames.size() / 2) {
                player.setTeam(team1);
                team1Players.add(player);
            } else {
                player.setTeam(team2);
                team2Players.add(player);
            }

            playerRepository.save(player);
        }

        team1.setPlayers(team1Players);
        team2.setPlayers(team2Players);

        teamRepository.save(team1);
        teamRepository.save(team2);

        Match match = new Match();
        match.setOrganizer(organizer);
        match.setTeam1(team1);
        match.setTeam2(team2);
        match.setFinished(false);

        return matchRepository.save(match);
    }

    public Match reshuffleTeams(Long matchId) {
        Match match = getMatchById(matchId);

        if (match.isFinished()) {
            throw new RuntimeException("Cannot reshuffle teams for a concluded match.");
        }

        List<Player> allPlayers = new ArrayList<>();
        allPlayers.addAll(match.getTeam1().getPlayers());
        allPlayers.addAll(match.getTeam2().getPlayers());

        Collections.shuffle(allPlayers);

        List<Player> team1Players = new ArrayList<>();
        List<Player> team2Players = new ArrayList<>();

        for (int i = 0; i < allPlayers.size(); i++) {
            Player player = allPlayers.get(i);
            if (i % 2 == 0) {
                player.setTeam(match.getTeam1());
                team1Players.add(player);
            } else {
                player.setTeam(match.getTeam2());
                team2Players.add(player);
            }
            playerRepository.save(player);
        }

        match.getTeam1().setPlayers(team1Players);
        match.getTeam2().setPlayers(team2Players);

        teamRepository.save(match.getTeam1());
        teamRepository.save(match.getTeam2());

        return saveMatch(match);
    }

    public Match updateMatchWinner(Long id, Long winnerId) {
        Match match = getMatchById(id);

        if (match.isFinished()) {
            throw new IllegalStateException("Match is already concluded and cannot be edited.");
        }

        Team winner = teamService.getTeamById(winnerId);

        match.setWinner(winner);
        match.setFinished(true);

        updateTeamResults(match);

        return matchRepository.save(match);
    }

    private void updateTeamResults(Match match) {
        Team winner = match.getWinner();
        if (winner != null) {
            winner.setResult("Win");

            Team loser = (match.getTeam1().equals(winner)) ? match.getTeam2() : match.getTeam1();
            loser.setResult("Loss");

            teamService.saveTeam(winner);
            teamService.saveTeam(loser);
        }
    }
}
