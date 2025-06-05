package org.MaViniciusDev;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
              SELECT r FROM Reservation r
              WHERE r.spaceId = :spaceId
              AND r.reservationDate = :date
              AND (
                  (r.reservationInit < :endTime AND r.reservationEnd > :startTime)
                  OR
                  (r.reservationInit >= :startTime AND r.reservationInit < :endTime)
              )
            """)
    List<Reservation> findConflictingReservations(
            @Param("spaceId") Long spaceId,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );

    List<Reservation> findBySpaceId(Long spaceId);

    List<Reservation> findByUserId(Long userId);
}
