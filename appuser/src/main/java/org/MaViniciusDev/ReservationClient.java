package org.MaViniciusDev;


import org.MaViniciusDev.DTO.ReservationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "Reservations")
public interface ReservationClient {
    @GetMapping("/api/v1/reservations/user/{userId}")
    List<ReservationDTO> getReservationsByUser(
            @PathVariable("userId") Long userId
    );
}