package com.SBTM.TournamentManager.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;

    @ManyToOne
    @JoinColumn(name = "team1_id")
    private Team team1;

    @ManyToOne
    @JoinColumn(name = "team2_id")
    private Team team2;

    @Column(name = "is_finished")
    private boolean finished;

    @ManyToOne
    @JoinColumn(name = "winner")
    private Team winner;
}
