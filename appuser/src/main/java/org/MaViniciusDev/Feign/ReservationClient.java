package org.MaViniciusDev.Feign;


import org.MaViniciusDev.DTO.ReservationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(name = "RESERVATIONSERVICE")
public interface ReservationClient {
    @GetMapping("/api/v1/reservations/user/{professorId}")
    List<ReservationDTO> getReservationsByUser(@PathVariable("id") Long id);
}