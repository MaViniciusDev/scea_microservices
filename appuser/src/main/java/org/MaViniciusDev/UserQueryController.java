package org.MaViniciusDev;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")

public class UserQueryController {

    private final AppUserService appUserService;

    public record UserInfoDTO(
            String email,
            String passwordHash,
            String role,
            boolean enabled
    ) {
    }

    @GetMapping("/by-email/{email}")
    public ResponseEntity<UserInfoDTO> getUserByEmail(@PathVariable("email") String email) {
        var userOpt = appUserService.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Usuário não encontrado: " + email
            );
        }

        AppUser user = userOpt.get();
        UserInfoDTO dto = new UserInfoDTO(
                user.getEmail(),
                user.getPassword(),
                user.getAppUserRole().name(),
                user.isEnabled()
        );

        return ResponseEntity.ok(dto);
    }
}
