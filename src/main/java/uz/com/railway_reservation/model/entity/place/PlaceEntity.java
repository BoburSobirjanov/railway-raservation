package uz.com.railway_reservation.model.entity.place;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import uz.com.railway_reservation.model.BaseModel;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "places")
public class PlaceEntity extends BaseModel {

    @Column(nullable = false)
    private Integer numberOfPlace;

    @Column(nullable = false)
    private Integer numberOfWagon;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlaceType type;

    @Column(nullable = false)
    private PlaceStatus status;

    @Column(nullable = false)
    private Double price;
}
