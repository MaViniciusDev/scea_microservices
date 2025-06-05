package org.MaViniciusDev.Feign;

import org.MaViniciusDev.DTO.AppUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "AppUser")
public interface UserServiceClient {

    @GetMapping("/by-email/")
    AppUserDTO getUserByEmail(@RequestParam("email") String email);
}
