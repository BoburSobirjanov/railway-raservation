package uz.com.railway_reservation.model.entity.wagon;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WagonType {

    BOXCAR(700.0),
    FLATCAR(310.0),
    GONDOLACAR(420.0),
    HOPPERCAR(550.0),
    REEFER(680.0),
    AUTORACK(800.0);

    private final double amount;
}
