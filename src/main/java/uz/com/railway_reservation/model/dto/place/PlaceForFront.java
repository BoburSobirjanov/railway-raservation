package uz.com.railway_reservation.model.dto.place;

import lombok.*;
import uz.com.railway_reservation.model.entity.place.PlaceStatus;
import uz.com.railway_reservation.model.entity.place.PlaceType;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PlaceForFront {

    private UUID id;

    private Double price;

    private PlaceType type;

    private PlaceStatus status;

    private Integer numberOfPlace;

    private Integer numberOfWagon;

}
