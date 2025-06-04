package org.MaViniciusDev.DTO;

import lombok.Data;

@Data
public class AppUserDTO {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final AppUserRole appUserRole;
    private final Boolean locked;
    private final Boolean enabled;
}