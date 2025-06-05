package org.MaViniciusDev.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Data
public class AppUserWithReservationDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<ReservationDTO> reservations;
}