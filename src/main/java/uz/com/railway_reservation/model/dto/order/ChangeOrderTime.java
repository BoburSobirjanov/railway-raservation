package uz.com.railway_reservation.model.dto.order;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ChangeOrderTime {

    private String startTime;

    private String fromWhere;

    private String toWhere;

    private String endTime;
}
