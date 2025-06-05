package org.MaViniciusDev.service;


import org.MaViniciusDev.AppUser;
import org.MaViniciusDev.AppUserRole;
import org.MaViniciusDev.AppUserService;
import org.MaViniciusDev.Email.EmailSender;
import org.MaViniciusDev.Registration.EmailValidator;
import org.MaViniciusDev.Registration.RegistrationRequest;
import org.MaViniciusDev.Registration.RegistrationService;
import org.MaViniciusDev.Registration.Token.ConfirmationToken;
import org.MaViniciusDev.Registration.Token.ConfirmationTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    @Mock
    private AppUserService appUserService;

    @Mock
    private EmailValidator emailValidator;

    @Mock
    private ConfirmationTokenService confirmationTokenService;

    @Mock
    private EmailSender emailSender;

    @InjectMocks

    private RegistrationService registrationService;

    private RegistrationRequest validRegistrationRequest;
    private RegistrationRequest invalidRegistrationRequest;

    private final String validEmail = "email@exemplo.com";
    private final String invalidEmail = "emailerrado";

    @BeforeEach
    void setUp() {
        validRegistrationRequest = new RegistrationRequest(
                "Luscas",
                "Kleber",
                validEmail,
                "senhasupersegura"
        );
        invalidRegistrationRequest = new RegistrationRequest(
                "George",
                "Pet",
                invalidEmail,
                "gatosnaoescrevemcodigo"
        );
    }

    @Test
    void register_ShouldTrowException_WhenEmailInvalid() {
        when(emailValidator.test(invalidEmail)).thenReturn(false);

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> {
                    registrationService.register(invalidRegistrationRequest);
                });

        assertEquals("Email não válido", ex.getMessage());
        verifyNoInteractions(appUserService, confirmationTokenService, emailSender);
    }

    @Test
    void register_ShouldCallSignUpUserAndSendEmail_WhenEmailValid() {
        when(emailValidator.test(validEmail)).thenReturn(true);

        String fakeToken = "token-super-especial";
        when(appUserService.signUpUser(any(AppUser.class))).thenReturn(fakeToken);

        String returnedToken = registrationService.register(validRegistrationRequest);

        assertEquals(fakeToken, returnedToken);

        verify(appUserService, times(1)).signUpUser(argThat(user ->
                user.getFirstName().equals("Luscas") &&
                        user.getLastName().equals("Kleber") &&
                        user.getEmail().equals(validEmail) &&
                        user.getAppUserRole() == null
        ));

        verify(emailSender,
                times(1))
                .send(eq(validEmail),
                        contains(
                                "http://localhost:8080/api/v1/registration/confirm?token="
                                        + fakeToken
                        ));
    }

    @Test
    void confirmToken_ShouldThrowException_WhenTokenNotFound() {

        when(confirmationTokenService.getToken("invalid"))
                .thenReturn(Optional.empty());

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> {
                    registrationService.confirmToken("invalid");
                });

        assertEquals("Token não encontrado",
                ex.getMessage());
        verifyNoInteractions(appUserService);
    }

    @Test
    void confirmToken_ShouldThrowException_WhenAlreadyConfirmed() {

        ConfirmationToken ct = new ConfirmationToken();
        ct.setToken("token-existente");
        ct.setConfirmedAt(LocalDateTime.now().minusMinutes(1));

        when(confirmationTokenService.getToken("token-existente")).thenReturn(Optional.of(ct));

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            registrationService.confirmToken("token-existente");
        });

        assertEquals("Email já confirmado", ex.getMessage());
        verifyNoInteractions(appUserService);
    }

    @Test
    void confirmToken_ShouldThrowException_WhenTokenExpired() {
        ConfirmationToken ct = new ConfirmationToken();
        ct.setToken("token-expirado");
        ct.setExpiresAt(LocalDateTime.now().minusMinutes(1));
        ct.setConfirmedAt(null);
        AppUser fakeUser = new AppUser("X", "Y", validEmail, "pass", null);
        ct.setAppUser(fakeUser);

        when(confirmationTokenService.getToken("token-expirado")).thenReturn(Optional.of(ct));

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            registrationService.confirmToken("token-expirado");
        });

        assertEquals("Token expirado", ex.getMessage());
        verifyNoInteractions(appUserService);
    }

    @Test
    void confirmToken_ShouldConfirmAndEnableUser_WhenTokenValid() {

        ConfirmationToken ct = new ConfirmationToken();
        ct.setToken("token-valid");
        ct.setExpiresAt(LocalDateTime.now().plusMinutes(10));
        ct.setConfirmedAt(null);
        AppUser fakeUser = new AppUser("X", "Y", validEmail, "pass", null);
        ct.setAppUser(fakeUser);

        when(confirmationTokenService.getToken("token-valid")).thenReturn(Optional.of(ct));


        registrationService.confirmToken("token-valid");


        verify(confirmationTokenService, times(1)).setConfirmedAt("token-valid");

        verify(appUserService, times(1)).enableAppUser(validEmail);
    }

    @Test
    void resendToken_ShouldThrowException_WhenUserNotFound() {
        when(appUserService.findByEmail(validEmail)).thenReturn(Optional.empty());

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            registrationService.resendToken(validEmail);
        });

        assertEquals("Usuário não encontrado", ex.getMessage());
        verifyNoInteractions(confirmationTokenService, emailSender);
    }

    @Test
    void resendToken_ShouldThrowException_WhenUserAlreadyEnabled() {
        AppUser user = new AppUser("Luscas", "Kleber", validEmail, "senhasupersegura", AppUserRole.USER);
        user.setEnabled(true);
        when(appUserService.findByEmail(validEmail)).thenReturn(Optional.of(user));

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            registrationService.resendToken(validEmail);
        });

        assertEquals("Conta já confirmada", ex.getMessage());
        verifyNoInteractions(confirmationTokenService, emailSender);
    }

    @Test
    void resendToken_ShouldCreateNewTokenAndSendEmail_WhenUserExistsAndNotEnabled() {
        AppUser user = new AppUser("Lucas", "Kleber", validEmail, "senhasupersegura", AppUserRole.USER);
        user.setEnabled(false);
        when(appUserService.findByEmail(validEmail)).thenReturn(Optional.of(user));

        String newToken = "novo-token";
        when(confirmationTokenService.createToken(user)).thenReturn(newToken);


        String returned = registrationService.resendToken(validEmail);

        assertEquals(newToken, returned);
        verify(confirmationTokenService, times(1)).createToken(user);
        verify(emailSender, times(1)).send(eq(validEmail),
                contains("http://localhost:8080/api/v1/registration/confirm?token="
                        + newToken));
    }
}

