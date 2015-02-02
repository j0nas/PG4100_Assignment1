package no.wact.jenjon13.assignment1.leaser;

import no.wact.jenjon13.assignment1.car.Car;

import java.util.List;

public class LeaserFactory {
    public static CarLeaser newLeaser(List<Car> carsList) {
        return new CarLeaser(carsList);
    }
}
