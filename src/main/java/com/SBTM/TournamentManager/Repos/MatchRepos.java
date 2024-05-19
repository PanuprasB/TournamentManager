package com.SBTM.TournamentManager.Repos;

import com.SBTM.TournamentManager.Model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MatchRepos extends JpaRepository<Match, Long> {
    List<Match> findByOrganizerId(Long organizerId);

    @Query("SELECT DISTINCT m FROM Match m WHERE m.organizer.id = :userId OR m.team1.id IN (SELECT pt.team.id FROM Player pt WHERE pt.user.id = :userId) OR m.team2.id IN (SELECT pt.team.id FROM Player pt WHERE pt.user.id = :userId)")
    List<Match> findMatchHistoryByUserId(Long userId);

    @Query("SELECT DISTINCT m FROM Match m WHERE m.team1.id IN (SELECT pt.team.id FROM Player pt WHERE pt.user.id = :userId) OR m.team2.id IN (SELECT pt.team.id FROM Player pt WHERE pt.user.id = :userId)")
    List<Match> findMatchesParticipatedByUserId(Long userId);

}
