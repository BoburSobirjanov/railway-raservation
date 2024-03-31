package uz.com.railway_reservation.model.dto.train;

import lombok.*;
import uz.com.railway_reservation.model.entity.train.TrainStatus;
import uz.com.railway_reservation.model.entity.wagon.WagonEntity;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TrainForFront {

    private UUID id;
    private String number;
    private Integer countOfWagon;
    private List<WagonEntity> wagons;
    private TrainStatus status;

}
