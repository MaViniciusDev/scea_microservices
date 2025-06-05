package org.MaViniciusDev.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class AcademicSpaceDTO {
    private Long Id;
    private String name;
    private String description;
    private int capacity;
    private String spaceType;
    private boolean hasComputer = false;
    private boolean active = true;
    private String disableReason;
}