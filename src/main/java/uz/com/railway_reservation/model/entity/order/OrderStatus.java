package uz.com.railway_reservation.model.entity.order;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PROGRESS,
    COMPLETED,
    CANCELED
}
