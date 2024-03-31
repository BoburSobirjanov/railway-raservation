package uz.com.railway_reservation.model.entity.train;

import jakarta.persistence.*;
import lombok.*;
import uz.com.railway_reservation.model.BaseModel;
import uz.com.railway_reservation.model.entity.wagon.WagonEntity;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "trains")
public class TrainEntity extends BaseModel {

    @Column(nullable = false, unique = true)
    private String trainNumber;

    @Column(nullable = false)
    private Integer countOfWagon;

    @OneToMany(mappedBy = "id",cascade = CascadeType.ALL)
    private List<WagonEntity> wagon;

    @Enumerated(EnumType.STRING)
    private TrainStatus status;

}
