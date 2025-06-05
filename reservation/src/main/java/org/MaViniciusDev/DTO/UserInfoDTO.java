package org.MaViniciusDev.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserInfoDTO {
    private Long id;
    private String email;
    private String passwordHash;
    private String role;
    private boolean enabled;

}