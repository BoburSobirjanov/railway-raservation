package uz.com.railway_reservation.model.place;

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
    private String numberOfPlace;

    @Column(nullable = false)
    private String numberOfWagon;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlaceType type;

    private PlaceStatus status;

    @Column(nullable = false)
    private Double price;
}
