package org.MaViniciusDev;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "academic_spaces")
public class AcademicSpaces {

    @SequenceGenerator(
            name = "academic_spaces_sequence",
            sequenceName = "academic_spaces_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "academic_spaces_sequence"
    )
    private Long id;

    @JsonProperty("nameCode")
    @Column(name = "name_code", nullable = false, unique = true)
    private String nameCode;

    @Column(nullable = false)
    private String name;

    @Column(length = 1500)
    private String description;

    @Min(value = 1, message = "Capacidade deve ser no mínimo 1")
    @Column(nullable = false)
    private int capacity;

    @Column(name = "space_type", nullable = false)
    private String spaceType;

    @Column(name = "has_computer", nullable = false)
    private boolean hasComputer = false;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @Column(name = "disable_reason", length = 500)
    private String disableReason;
}