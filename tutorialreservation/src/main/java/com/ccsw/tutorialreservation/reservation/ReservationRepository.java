package com.ccsw.tutorialreservation.reservation;

import com.ccsw.tutorialreservation.reservation.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

/**
 * @author ccsw
 *
 */
public interface ReservationRepository extends CrudRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation> {

    @Override
    Page<Reservation> findAll(Specification<Reservation> spec, Pageable pageable);

    @Query(value = "SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " + "FROM Reservation r " + "WHERE r.idGame = :gameId AND ( r.startDate <= :endDate AND r.endDate >= :startDate)"
            + "AND (r.id <> :reservationId OR :reservationId IS NULL)")
    boolean existsOverlappingReservationByGame(Long reservationId, Long gameId, LocalDate startDate, LocalDate endDate);

    @Query(value = "SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " + "FROM Reservation r " + "WHERE r.idClient = :clientId " + "AND ( r.startDate <= :endDate AND r.endDate >= :startDate) "
            + "AND (r.id <> :reservationId OR :reservationId IS NULL)")
    boolean existsOverlappingReservationByClient(Long reservationId, Long clientId, LocalDate startDate, LocalDate endDate);
}
