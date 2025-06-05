package org.MaViniciusDev;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/create")
    public ResponseEntity<Reservation> createReservation(
            @RequestBody Reservation reservation,
            @RequestHeader("X-Authenticated-User") String professorEmail
    ) {
        Reservation created = reservationService.makeReservation(reservation, professorEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable("id") Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok("Reserva deletada com sucesso.");
    }

    @GetMapping("/user/email/{email}")
    public ResponseEntity<List<Reservation>> getUserReservationsByEmail(
            @PathVariable("email") String email
    ) {
        List<Reservation> reservas = reservationService.getByProfessorEmail(email);
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/space/{spaceId}")
    public ResponseEntity<List<Reservation>> getReservationsBySpace(@PathVariable("spaceId") Long spaceId) {
        List<Reservation> reservas = reservationService.getBySpaceId(spaceId);
        return ResponseEntity.ok(reservas);
    }
}