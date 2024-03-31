package uz.com.railway_reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.com.railway_reservation.model.dto.wagon.WagonDto;
import uz.com.railway_reservation.model.dto.wagon.WagonForFront;
import uz.com.railway_reservation.response.StandardResponse;
import uz.com.railway_reservation.service.WagonService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wagon")
public class WagonController {
    private final WagonService wagonService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    public StandardResponse<WagonForFront> save(
            @RequestBody WagonDto wagonDto,
            Principal principal
            ){
        return wagonService.save(wagonDto, principal);
    }
}
