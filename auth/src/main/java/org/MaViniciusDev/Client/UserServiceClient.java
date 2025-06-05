package org.MaViniciusDev.Client;

import org.MaViniciusDev.Token.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "AppUser", path = "/api/v1/users")
public interface UserServiceClient {
    @GetMapping("/by-email/{email}")
    UserResponse findByEmail(@PathVariable("email") String email);

}