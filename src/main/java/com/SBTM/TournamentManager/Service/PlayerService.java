package com.SBTM.TournamentManager.Service;

import com.SBTM.TournamentManager.Model.Player;
import com.SBTM.TournamentManager.Repos.PlayerRepos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepos playerRepository;

    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player getPlayerById(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new RuntimeException("Player not found"));
    }

}
