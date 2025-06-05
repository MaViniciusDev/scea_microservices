package org.MaViniciusDev;

import lombok.RequiredArgsConstructor;
import org.MaViniciusDev.DTO.AcademicSpaceDTO;
import org.MaViniciusDev.DTO.UserInfoDTO;
import org.MaViniciusDev.Exception.ReservationConflictException;
import org.MaViniciusDev.Exception.ResourceNotFoundException;
import org.MaViniciusDev.Feign.SpaceServiceClient;
import org.MaViniciusDev.Feign.UserServiceClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserServiceClient userServiceClient;
    private final SpaceServiceClient spaceServiceClient;

    public Reservation makeReservation(Reservation reservation, String professorEmail) {

        UserInfoDTO professor = userServiceClient.getUserByEmail(professorEmail);
        if (professor == null) {
            throw new ResourceNotFoundException("Usuário não encontrado: " + professorEmail);
        }

        reservation.setProfessorId(professor.getId());

        AcademicSpaceDTO space = spaceServiceClient.getSpaceById(reservation.getAcademicSpacesId());
        if (space == null) {
            throw new ResourceNotFoundException("Espaço não encontrado: id=" + reservation.getAcademicSpacesId());
        }

        LocalDate date = reservation.getReservationDate();
        LocalTime init = reservation.getReservationInit();
        LocalTime end = reservation.getReservationEnd();

        if (!end.isAfter(init)) {
            throw new IllegalArgumentException("Horário final deve ser após o horário inicial.");
        }

        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Não é possível criar reservas em datas passadas.");
        }

        List<Reservation> conflicts = reservationRepository
                .findConflictingReservations(reservation.getAcademicSpacesId(), date, init, end);

        if (!conflicts.isEmpty()) {
            throw new ReservationConflictException("Já existe reserva conflitando neste horário.");
        }

        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        Reservation r = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada: id=" + id));
        reservationRepository.delete(r);
    }

    public List<Reservation> getByProfessorId(Long professorId) {
        return reservationRepository.findByProfessorId(professorId);
    }

    public List<Reservation> getBySpaceId(Long spaceId) {
        return reservationRepository.findByAcademicSpacesId(spaceId);
    }
}
