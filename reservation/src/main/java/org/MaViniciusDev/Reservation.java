package org.MaViniciusDev;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity

public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long spaceId;
    private Long userId;

    private LocalDate reservationDate;
    private LocalTime reservationInit;
    private LocalTime reservationEnd;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationType type;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column(length = 500)
    private String observations;

    private boolean confirmUse = false;
    private boolean ended = false;

}