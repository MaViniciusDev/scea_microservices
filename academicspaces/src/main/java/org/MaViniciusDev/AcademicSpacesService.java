package org.MaViniciusDev;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AcademicSpacesService {

    private final AcademicSpacesRepository repo;

    @Transactional
    public AcademicSpaces createSpace(AcademicSpaces space) {
        if (repo.existsByNameCode(space.getNameCode().trim())) {
            throw new IllegalStateException("Código já existe: " + space.getNameCode());
        }
        return repo.save(space);
    }

    @Transactional
    public AcademicSpaces updateSpace(Long id, AcademicSpaces space) {
        AcademicSpaces existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Espaço não encontrado: " + id));

        String newCode = space.getNameCode().trim();
        if (!existing.getNameCode().equalsIgnoreCase(newCode)
                && repo.existsByNameCode(newCode)) {
            throw new IllegalStateException("Outro espaço já usa o código: " + newCode);
        }

        existing.setNameCode(newCode);
        existing.setName(space.getName().trim());
        existing.setDescription(space.getDescription());
        existing.setCapacity(space.getCapacity());
        existing.setSpaceType(space.getSpaceType().trim());
        existing.setHasComputer(space.isHasComputer());
        existing.setActive(space.isActive());
        existing.setDisableReason(space.getDisableReason());
        return repo.save(existing);
    }

    public List<AcademicSpaces> getActiveSpaces() {
        return repo.findByActiveTrue();
    }

    public List<AcademicSpaces> getAllSpaces() {
        return repo.findAll();
    }

    @Transactional
    public AcademicSpaces updateAvailability(Long id, boolean active, String disableReason) {
        AcademicSpaces existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Espaço não encontrado: " + id));
        existing.setActive(active);
        existing.setDisableReason(active ? null : disableReason);
        return repo.save(existing);
    }

    @Transactional
    public void deleteSpace(Long id) {
        AcademicSpaces existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Espaço não encontrado: " + id));
        repo.delete(existing);
    }


}