package uz.com.railway_reservation.model.entity.order;

import jakarta.persistence.*;
import lombok.*;
import uz.com.railway_reservation.model.BaseModel;
import uz.com.railway_reservation.model.entity.user.UserEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "orders")
public class OrderEntity extends BaseModel {

    @ManyToOne
    private UserEntity owner;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private String fromWhere;

    @Column(nullable = false)
    private String toWhere;

    private LocalDateTime startTime;

    @Column(nullable = false)
    private UUID wagonId;

    private LocalDateTime endTime;

    private Double price;
}
