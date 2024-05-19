package com.SBTM.TournamentManager.Service;

import com.SBTM.TournamentManager.Model.Team;

import com.SBTM.TournamentManager.Repos.TeamRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepos teamRepository;

    public Team saveTeam(Team team) {
        return teamRepository.save(team);
    }


    public Team getTeamById(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new RuntimeException("Team not found"));
    }
}