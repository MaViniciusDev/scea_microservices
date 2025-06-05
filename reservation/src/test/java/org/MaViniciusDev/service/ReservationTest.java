package org.MaViniciusDev.service;

import org.MaViniciusDev.DTO.AcademicSpaceDTO;
import org.MaViniciusDev.DTO.UserInfoDTO;
import org.MaViniciusDev.ReservationRepository;
import org.MaViniciusDev.ReservationService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReservationTest {

    @Mock
    private ReservationService reservationService;

    @Mock
    private AcademicSpaceDTO academicSpaceDTO;

    @Mock
    private UserInfoDTO userInfoDTO;


}
