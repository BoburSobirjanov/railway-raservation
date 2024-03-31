package uz.com.railway_reservation.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.com.railway_reservation.repository.OrderRepository;
import uz.com.railway_reservation.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
}
