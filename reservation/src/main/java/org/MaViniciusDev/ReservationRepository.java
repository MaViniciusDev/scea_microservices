package org.MaViniciusDev;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
              SELECT r FROM Reservation r
              WHERE r.academicSpacesId = :spaceId
              AND r.reservationDate = :date
              AND (
                  (r.reservationInit < :endTime AND r.reservationEnd > :startTime)
                  OR
                  (r.reservationInit >= :startTime AND r.reservationInit < :endTime)
              )
            """)
    List<Reservation> findConflictingReservations(
            Long spaceId, LocalDate date, LocalTime startTime, LocalTime endTime
    );

    List<Reservation> findByProfessorId(Long professorId);

    List<Reservation> findByAcademicSpacesId(Long spaceId);
}
