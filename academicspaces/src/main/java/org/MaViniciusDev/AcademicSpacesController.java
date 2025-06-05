package org.MaViniciusDev;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/spaces")
@AllArgsConstructor
public class AcademicSpacesController {

    private final AcademicSpacesService academicSpacesService;

    @PostMapping("/create")
    public ResponseEntity<?> createSpace(@RequestBody AcademicSpaces academicSpaces) {
        try {
            return ResponseEntity.ok(academicSpacesService.createSpace(academicSpaces));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao criar espaço acadêmico: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSpace(@PathVariable Long id,
                                         @RequestBody AcademicSpaces academicSpaces) {
        try {
            return ResponseEntity.ok(academicSpacesService.updateSpace(id, academicSpaces));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao atualizar espaço acadêmico: " + e.getMessage());
        }
    }

    @GetMapping("/active")
    public ResponseEntity<List<AcademicSpaces>> getActiveSpaces() {
        return ResponseEntity.ok(academicSpacesService.getActiveSpaces());
    }

    @GetMapping("/all")
    public ResponseEntity<List<AcademicSpaces>> getAllSpaces() {
        return ResponseEntity.ok(academicSpacesService.getAllSpaces());
    }

    @PutMapping("/update-availability/{id}")
    public ResponseEntity<?> updateAvailability(@PathVariable("id") Long id,
                                                @RequestParam boolean active) {
        try {
            AcademicSpaces updated = academicSpacesService.updateAvailability(id, active, null);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Erro ao atualizar disponibilidade: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSpace(@PathVariable("id") Long id) {
        try {
            academicSpacesService.deleteSpace(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao excluir espaço: " + e.getMessage());
        }
    }
}
