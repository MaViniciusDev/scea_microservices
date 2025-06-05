package org.MaViniciusDev;

import lombok.RequiredArgsConstructor;
import org.MaViniciusDev.Client.UserServiceClient;
import org.MaViniciusDev.Token.DTO.AuthRequest;
import org.MaViniciusDev.Token.DTO.AuthResponse;
import org.MaViniciusDev.Token.UserResponse;
import org.MaViniciusDev.Security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class LoginController {
    private final JwtService jwtService;
    private final UserServiceClient userServiceClient;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        try {
            UserResponse user = userServiceClient.findByEmail(authRequest.getEmail());

            if (!user.isEnabled() || user.isLocked()) {
                return ResponseEntity.status(403).body("Usuário bloqueado ou desabilitado");
            }

            if (!passwordEncoder.matches(authRequest.getPassword(), user.getPasswordHash())) {
                return ResponseEntity.status(401).body("Senha inválida");
            }

            String jwt = jwtService.generateToken(user.getEmail());

            return ResponseEntity.ok(new AuthResponse(jwt));

        } catch (Exception e) {
            return ResponseEntity.status(404).body("Usuário não encontrado");
        }
    }
}
