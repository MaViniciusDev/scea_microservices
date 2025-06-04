package org.MaViniciusDev;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.MaViniciusDev.Registration.Token.ConfirmationTokenService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    public Optional<AppUser> findByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return appUserRepository.findByEmail(email).isPresent();
    }

    public List<AppUser> findAll() {
        return appUserRepository.findAll();
    }

    public void deleteById(Long id) {
        appUserRepository.deleteById(id);
    }

    public AppUser updateRole(Long id, String newRole) {
        AppUser u = appUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado! " + id));
        u.setAppUserRole(AppUserRole.valueOf(newRole));
        return appUserRepository.save(u);
    }

    public void updateName(String email, String firstName, String lastName) {
        AppUser u = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado! " + email));
        u.setFirstName(firstName);
        u.setLastName(lastName);
        appUserRepository.save(u);
    }

    public String signUpUser(AppUser appUser) {
        if (existsByEmail(appUser.getEmail())) {
            throw new IllegalStateException("Email já cadastrado");
        }
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        boolean isFirstUser = appUserRepository.count() == 0;
        appUser.setAppUserRole(isFirstUser ? AppUserRole.ADMIN : AppUserRole.USER);
        appUserRepository.save(appUser);
        return confirmationTokenService.createToken(appUser);
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }
}