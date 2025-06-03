package org.MaViniciusDev;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;

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
    public AppUser updateRole(Long id, String newRole){
        AppUser u = appUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado! " + id));
        u.setAppUserRole(AppUserRole.valueOf(newRole));
        return appUserRepository.save(u);
    }

    public void updateName(String email, String firtName, String lastName) {
        AppUser u = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado! " + email));
                u.setFirstName(firtName);
                u.setLastName(lastName);
                appUserRepository.save(u);
    }
}
