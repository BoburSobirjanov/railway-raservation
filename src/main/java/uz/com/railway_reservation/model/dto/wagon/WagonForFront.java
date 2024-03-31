package uz.com.railway_reservation.model.dto.wagon;

import lombok.*;
import uz.com.railway_reservation.model.entity.train.TrainEntity;
import uz.com.railway_reservation.model.entity.wagon.WagonStatus;
import uz.com.railway_reservation.model.entity.wagon.WagonType;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class WagonForFront {

    private UUID id;

    private WagonType type;

    private WagonStatus status;

    private String number;

    private UUID trainId;

    private Double price;

    private String description;

}
