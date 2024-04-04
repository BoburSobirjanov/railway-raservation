package uz.com.railway_reservation.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.com.railway_reservation.exception.DataNotFoundException;
import uz.com.railway_reservation.exception.NotAcceptableException;
import uz.com.railway_reservation.model.dto.order.OrderDto;
import uz.com.railway_reservation.model.dto.order.OrderForFront;
import uz.com.railway_reservation.model.entity.order.OrderEntity;
import uz.com.railway_reservation.model.entity.order.OrderStatus;
import uz.com.railway_reservation.model.entity.user.UserEntity;
import uz.com.railway_reservation.model.entity.wagon.WagonEntity;
import uz.com.railway_reservation.repository.OrderRepository;
import uz.com.railway_reservation.repository.UserRepository;
import uz.com.railway_reservation.repository.WagonRepository;
import uz.com.railway_reservation.response.StandardResponse;
import uz.com.railway_reservation.response.Status;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final WagonRepository wagonRepository;

    public StandardResponse<OrderForFront> save(OrderDto orderDto, Principal principal){
        UserEntity user = userRepository.findUserEntityByEmail(principal.getName());
        WagonEntity wagon = wagonRepository.findWagonEntityById(UUID.fromString(orderDto.getWagonId()));
        if (wagon==null){
            throw new DataNotFoundException("Wagon not found same this id!");
        }
        List<OrderEntity> orderEntities = wagon.getOrders();
        for (OrderEntity order: orderEntities) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime startTime = LocalDateTime.parse(orderDto.getStartTime(),dateTimeFormatter);
            if((startTime.isAfter(order.getStartTime()) && startTime.isBefore(order.getEndTime()))
                    || startTime.isEqual(order.getStartTime()) || startTime.isBefore(LocalDateTime.now())){
            throw new NotAcceptableException("Wagon is busy in this time!");
        }
    }
        if (LocalDateTime.parse(orderDto.getStartTime()).isBefore(LocalDateTime.now())){
            throw new NotAcceptableException("Time is not available!");
        }
        OrderEntity orderEntity = modelMapper.map(orderDto, OrderEntity.class);
        orderEntity.setStatus(OrderStatus.PROGRESS);
        orderEntity.setPrice(wagon.getPrice());
        orderEntity.setStartTime(LocalDateTime.parse(orderDto.getStartTime()));
        orderEntity.setFromWhere(orderDto.getFromWhere());
        orderEntity.setToWhere(orderDto.getToWhere());
        orderEntity.setWagonId(UUID.fromString(orderDto.getWagonId()));
        orderEntity.setCreatedBy(user.getId());
        orderEntity.setOwner(user);
        orderEntity.setEndTime(LocalDateTime.parse(orderDto.getStartTime()).plusHours(5));
        orderRepository.save(orderEntity);
        OrderForFront orderForFront = modelMapper.map(orderEntity, OrderForFront.class);
        return StandardResponse.<OrderForFront>builder()
                .status(Status.SUCCESS)
                .message("Order created!")
                .data(orderForFront)
                .build();
   }

   public StandardResponse<OrderForFront> cancelOrder(UUID id,Principal principal){
        UserEntity user = userRepository.findUserEntityByEmail(principal.getName());
        OrderEntity order = orderRepository.findOrderEntityById(id);
        if (order==null){
            throw new DataNotFoundException("Order not found!");
        }
        order.setStatus(OrderStatus.CANCELED);
        order.setChangeStatusBy(user.getId());
       OrderEntity save = orderRepository.save(order);
       OrderForFront orderForFront = modelMapper.map(save, OrderForFront.class);
       return StandardResponse.<OrderForFront>builder()
               .data(orderForFront)
               .status(Status.SUCCESS)
               .message("CANCELED")
               .build();
   }
}
