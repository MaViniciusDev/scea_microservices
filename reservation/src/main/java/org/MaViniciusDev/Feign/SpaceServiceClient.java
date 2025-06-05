package org.MaViniciusDev.Feign;

import org.MaViniciusDev.DTO.AcademicSpaceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Spaces")
public interface SpaceServiceClient {

    @GetMapping("/api/v1/spaces/{id}")
    AcademicSpaceDTO getSpaceById(@PathVariable("id") Long id);
}