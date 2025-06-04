package org.MaViniciusDev.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AppUserWithReservationDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<ReservationDTO> reservations;
}