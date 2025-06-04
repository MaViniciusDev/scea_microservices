package org.MaViniciusDev.Feign;

import org.MaViniciusDev.DTO.AppUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("USERSERVICE")
public interface UserServiceClient {

    @GetMapping("/api/v1/users/by-email")
    AppUserDTO getUserByEmail(@RequestParam("email") String email);
}
