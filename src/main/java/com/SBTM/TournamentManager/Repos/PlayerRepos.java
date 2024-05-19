package com.SBTM.TournamentManager.Repos;

import com.SBTM.TournamentManager.Model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepos extends JpaRepository<Player, Long> {
}