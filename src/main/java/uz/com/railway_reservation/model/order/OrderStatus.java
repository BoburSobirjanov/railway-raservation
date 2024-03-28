package uz.com.railway_reservation.model.order;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PROGRESS,
    COMPLETED,
    CANCELED
}
