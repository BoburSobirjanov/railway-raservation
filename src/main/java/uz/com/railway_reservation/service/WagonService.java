package uz.com.railway_reservation.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.com.railway_reservation.exception.DataHasAlreadyExistException;
import uz.com.railway_reservation.exception.DataNotFoundException;
import uz.com.railway_reservation.exception.NotAcceptableException;
import uz.com.railway_reservation.model.dto.wagon.WagonDto;
import uz.com.railway_reservation.model.dto.wagon.WagonForFront;
import uz.com.railway_reservation.model.entity.train.TrainEntity;
import uz.com.railway_reservation.model.entity.user.UserEntity;
import uz.com.railway_reservation.model.entity.wagon.WagonEntity;
import uz.com.railway_reservation.model.entity.wagon.WagonStatus;
import uz.com.railway_reservation.model.entity.wagon.WagonType;
import uz.com.railway_reservation.repository.TrainRepository;
import uz.com.railway_reservation.repository.UserRepository;
import uz.com.railway_reservation.repository.WagonRepository;
import uz.com.railway_reservation.response.StandardResponse;
import uz.com.railway_reservation.response.Status;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WagonService {
    private final ModelMapper modelMapper;
    private final WagonRepository wagonRepository;
    private final UserRepository userRepository;
    private final TrainRepository trainRepository;

    public StandardResponse<WagonForFront> save(WagonDto wagonDto, Principal principal){
        UserEntity userEntity = userRepository.findUserEntityByEmail(principal.getName());
        checkHasWagon(wagonDto.getNumber());

        List<WagonEntity> wagonEntities = wagonRepository.findWagonEntityByTrainId(UUID.fromString(wagonDto.getTrainId()));
        TrainEntity train = trainRepository.findTrainEntityById(UUID.fromString(wagonDto.getTrainId()));

        if (train==null){
            throw new DataNotFoundException("Train not found same this id!");
        }
        if (wagonEntities.size()>=train.getCountOfWagon()){
            throw new NotAcceptableException("There are enough wagons on the train!");
        }


        WagonEntity wagon = modelMapper.map(wagonDto, WagonEntity.class);
        wagon.setCapacity(wagon.getCapacity());
        try{
        wagon.setType(WagonType.valueOf(wagonDto.getType()));
        }catch (Exception e){
           throw new NotAcceptableException("Invalid wagon type!");
        }
        wagon.setNumber(wagonDto.getNumber());
        wagon.setTrainId(UUID.fromString(wagonDto.getTrainId()));
        wagon.setCreatedBy(userEntity.getId());
        wagon.setStatus(WagonStatus.EMPTY);
        wagon.setDescription(wagonDto.getDescription());
        wagon.setPrice(wagon.getType().getAmount());
        wagon.setCapacity(wagonDto.getCapacity());
        WagonEntity wagonEntity = wagonRepository.save(wagon);
        WagonForFront wagonForFront = modelMapper.map(wagonEntity, WagonForFront.class);
        return StandardResponse.<WagonForFront>builder()
                .status(Status.SUCCESS)
                .message("Wagon added!")
                .data(wagonForFront)
                .build();
    }
    public void checkHasWagon(String number){
        WagonEntity wagon = wagonRepository.findWagonEntityByNumber(number);
        if (wagon!=null){
            throw new DataHasAlreadyExistException("Wagon has already added!");
        }
    }
}
