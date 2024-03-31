package uz.com.railway_reservation.model.entity.wagon;

import jakarta.persistence.*;
import lombok.*;
import uz.com.railway_reservation.model.BaseModel;
import uz.com.railway_reservation.model.entity.train.TrainEntity;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "wagons")
public class WagonEntity extends BaseModel {

    @Column(nullable = false,unique = true)
    private String number;

    @Column(nullable = false)
    private String capacity;

    private UUID trainId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WagonType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WagonStatus status;

    private Double price;

    @Column(nullable = false)
    private String description;

}
