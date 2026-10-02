package com.ccsw.tutorialreservation.reservation.model;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * @author ccsw
 *
 */
@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "game_id", nullable = false)
    private Long idGame;

    @Column(name = "client_id", nullable = false)
    private Long idClient;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    /**
     * @return id
     */
    public Long getId() {

        return this.id;
    }

    /**
     * @param id new value of {@link #getId}.
     */
    public void setId(Long id) {

        this.id = id;
    }

    /**
     * @return game
     */
    public Long getIdGame() {

        return this.idGame;
    }

    /**
     * @param idGame new value of {@link #getIdGame}.
     */
    public void setIdGame(Long idGame) {

        this.idGame = idGame;
    }

    /**
     * @return client
     */
    public Long getIdClient() {

        return this.idClient;
    }

    /**
     * @param idClient new value of {@link #getIdClient}.
     */
    public void setIdClient(Long idClient) {

        this.idClient = idClient;
    }

    /**
     * @return startDate
     */
    public LocalDate getStartDate() {

        return this.startDate;
    }

    /**
     * @param startDate new value of {@link #getStartDate}
     */
    public void setStartDate(LocalDate startDate) {

        this.startDate = startDate;
    }

    /**
     * @return endDate
     */
    public LocalDate getEndDate() {

        return this.endDate;
    }

    /**
     * @param endDate new value of {@link #getEndDate}
     */
    public void setEndDate(LocalDate endDate) {

        this.endDate = endDate;
    }
}
