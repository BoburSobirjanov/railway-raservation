package uz.com.railway_reservation.model.dto.place;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PlaceDto {

    private Integer numberOfPlace;

    private Integer numberOfWagon;

    private String type;

    private Double price;

}
