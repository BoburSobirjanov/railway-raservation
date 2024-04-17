package uz.com.railway_reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.com.railway_reservation.model.dto.order.ChangeOrderTime;
import uz.com.railway_reservation.model.dto.order.OrderDto;
import uz.com.railway_reservation.model.dto.order.OrderForFront;
import uz.com.railway_reservation.model.entity.order.OrderEntity;
import uz.com.railway_reservation.response.StandardResponse;
import uz.com.railway_reservation.service.OrderService;

import java.security.Principal;
import java.util.List;
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

    @DeleteMapping("{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<OrderForFront> delete(
            @PathVariable UUID id,
            Principal principal
    ){
        return orderService.delete(id, principal);
    }

    @PostMapping("/get-canceled-orders")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderEntity> getCanceledOrders(
            @RequestParam String cancel
    ){
        return orderService.getCanceledOrders(cancel);
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderEntity> getAll(){
        return orderService.getAll();
    }

    @PostMapping("/{id}/change-order-time")
    public StandardResponse<OrderForFront> changeOrderTime(
            @RequestBody ChangeOrderTime change,
            @PathVariable UUID id,
            Principal principal
            ){
       return orderService.changeOrderTime(id, change,principal);
    }
}
