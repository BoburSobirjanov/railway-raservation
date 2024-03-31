package uz.com.railway_reservation.model.dto.train;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TrainDto {

    private String number;
    private Integer countOfWagon;

}
