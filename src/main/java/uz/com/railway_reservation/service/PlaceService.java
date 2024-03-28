package uz.com.railway_reservation.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final ModelMapper modelMapper;
}
