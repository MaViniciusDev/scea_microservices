package org.MaViniciusDev;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor

public class AppUserController {

    private final AppUserService appUserService;

    public record UserDTO(Long id,
                          String firstName,
                          String lastName,
                          String email,
                          String appUserRole){}

    public record RoleDTO(String role){}

    public record ProfileDTO(String firstName,
                             String lastName){}

    @GetMapping("/exists")
    public Map<String, Object> checkUser(@RequestParam String email){
        Map<String, Object> response = new HashMap<>();
        appUserService.findByEmail(email).ifPresentOrElse(
                u ->{
                    response.put("exists", true);
                    response.put("confirmed", u.isEnabled());
                    response.put("firstName", u.getFirstName());
                    response.put("lastName", u.getLastName());
                },
                () -> {
                    response.put("exists", false);
                    response.put("confirmed", false);
                }
        );

        return response;
    }

    @GetMapping
    public List<UserDTO> getAllUsers(
            @RequestHeader("X-Authenticated-User") String usernameHeader
    ){
        return appUserService.findAll().stream()
                .map(u -> new UserDTO(
                        u.getId(),
                        u.getFirstName(),
                        u.getLastName(),
                        u.getEmail(),
                        u.getAppUserRole().name()

                ))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id,
            @RequestHeader("X-Authenticated-User")
            String usernameHeader
    ){

        appUserService.deleteById(id);
        return ResponseEntity.ok().build();

    }

    @PatchMapping("/{id}/role")
    public UserDTO updateRole(
            @PathVariable Long id,
            @RequestBody RoleDTO dto,
            @RequestHeader("X-Authenticated-User")
            String usernameHeader
    ){
        AppUser u = appUserService.updateRole(id, dto.role());
        return new UserDTO(
                u.getId(),
                u.getFirstName(),
                u.getLastName(),
                u.getEmail(),
                u.getAppUserRole().name()
        );
    }

    @PutMapping("/profile")
    public ResponseEntity<Void> updateProfile(
            @RequestBody ProfileDTO dto,
            @RequestHeader("X-Authenticated-User")
            String usernameHeader
    ){

        appUserService.updateName(usernameHeader, dto.firstName(), dto.lastName());
        return ResponseEntity.ok().build();
    }


}
