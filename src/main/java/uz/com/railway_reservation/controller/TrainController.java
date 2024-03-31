package uz.com.railway_reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.com.railway_reservation.model.dto.train.TrainDto;
import uz.com.railway_reservation.model.dto.train.TrainForFront;
import uz.com.railway_reservation.response.StandardResponse;
import uz.com.railway_reservation.service.TrainService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/train")
public class TrainController {
    private final TrainService trainService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    public StandardResponse<TrainForFront> save(
            @RequestBody TrainDto trainDto,
            Principal principal
            ){
        return trainService.save(trainDto,principal);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    public StandardResponse<String> delete(
            @RequestParam String number,
            Principal principal
    ){
        return trainService.delete(number, principal);
    }
}
