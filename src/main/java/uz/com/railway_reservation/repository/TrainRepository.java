package uz.com.railway_reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.com.railway_reservation.model.entity.train.TrainEntity;


import java.util.UUID;
@Repository
public interface TrainRepository extends JpaRepository<TrainEntity, UUID> {
    @Query("select u from trains as u where u.trainNumber=?1 and u.isDeleted=false")
    TrainEntity findTrainEntityByTrainNumber(String number);
    @Query("select u from trains as u where u.isDeleted=false and u.id=?1")
    TrainEntity findTrainEntityById(UUID id);

}