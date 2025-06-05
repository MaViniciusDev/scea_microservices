package org.MaViniciusDev.Service;

import lombok.RequiredArgsConstructor;
import org.MaViniciusDev.Token.ConfirmationToken;
import org.MaViniciusDev.Token.ConfirmationTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public void setConfirmedAt(String token) {
        confirmationTokenRepository.findByToken(token).ifPresent(t -> {
            t.setConfirmedAt(LocalDateTime.now());
            confirmationTokenRepository.save(t);
        });
    }
}