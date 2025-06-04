package org.MaViniciusDev.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class ReservationDTO {

    private Long id;
    private Long academicSpaceId;
    private Long professorId;

    private LocalDate reservationDate;
    private LocalTime reservationInit;
    private LocalTime reservationEnd;

    private ReservationType type;
    private LocalDateTime createdAt;

    private String observations;

    private boolean confirmUse;
    private boolean ended;

    public enum ReservationType {
        AULA, EVENTO
    }
}
