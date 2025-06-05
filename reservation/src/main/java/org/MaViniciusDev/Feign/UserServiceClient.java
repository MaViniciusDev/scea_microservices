package org.MaViniciusDev.Feign;


import org.MaViniciusDev.DTO.UserInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "AppUser", path = "/api/v1/users")
public interface UserServiceClient {

    @GetMapping("/by-email/{email}")
    UserInfoDTO getUserByEmail(@PathVariable("email") String email);
}
