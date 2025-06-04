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

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation, @RequestBody String professoremail) {
        Reservation created = reservationService.makeReservation(reservation, professoremail);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok("Reserva deletada com sucesso.");
    }

    @GetMapping("/user/{professorId}")
    public ResponseEntity<List<Reservation>> getUserReservations(@PathVariable Long professorId) {
        List<Reservation> reservas = reservationService.getByProfessorId(professorId);
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/space/{spaceId}")
    public ResponseEntity<List<Reservation>> getReservationsBySpace(@PathVariable Long spaceId) {
        List<Reservation> reservas = reservationService.getBySpaceId(spaceId);
        return ResponseEntity.ok(reservas);
    }
}