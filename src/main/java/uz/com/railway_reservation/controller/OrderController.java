package uz.com.railway_reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.com.railway_reservation.model.dto.order.OrderDto;
import uz.com.railway_reservation.model.dto.order.OrderForFront;
import uz.com.railway_reservation.response.StandardResponse;
import uz.com.railway_reservation.service.OrderService;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/save")
    public StandardResponse<OrderForFront> save(
            @RequestBody OrderDto orderDto,
            Principal principal
            ){
        return orderService.save(orderDto, principal);
    }

    @PostMapping("/{id}/cancel")
    public StandardResponse<OrderForFront> cancel(
            @PathVariable UUID id,
            Principal principal
    ){
        return orderService.cancelOrder(id, principal);
    }
}
