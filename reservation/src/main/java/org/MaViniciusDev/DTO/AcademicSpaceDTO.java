package org.MaViniciusDev.DTO;

import lombok.Data;

@Data
public class AcademicSpaceDTO {
    private Long id;
    private String name;
    private String description;
    private int capacity;
    private String spaceType;
    private boolean hasComputer = false;
    private boolean active = true;
    private String disableReason;
}