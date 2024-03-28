package uz.com.railway_reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.com.railway_reservation.service.PlaceService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/place")
public class PlaceController {
    private final PlaceService placeService;
}
