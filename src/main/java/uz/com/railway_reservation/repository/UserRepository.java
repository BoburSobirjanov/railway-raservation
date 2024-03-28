package uz.com.railway_reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.com.railway_reservation.model.entity.user.UserEntity;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    @Query("select u from users as u where u.isDeleted=false and u.email=?1")
    UserEntity findUserEntityByEmail(String email);
    @Query("select u from users as u where u.isDeleted=false and u.number=?1")
    Optional<UserEntity> findUserEntityByNumber(String number);
}
