package com.SBTM.TournamentManager.Repos;

import com.SBTM.TournamentManager.Model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepos extends JpaRepository<Team, Long> {

}