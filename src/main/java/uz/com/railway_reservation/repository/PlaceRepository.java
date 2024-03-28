package uz.com.railway_reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.com.railway_reservation.model.entity.place.PlaceEntity;

import java.util.UUID;

@Repository
public interface PlaceRepository extends JpaRepository<PlaceEntity, UUID> {
}
