package uz.com.railway_reservation.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.com.railway_reservation.exception.DataHasAlreadyExistException;
import uz.com.railway_reservation.exception.DataNotFoundException;
import uz.com.railway_reservation.model.dto.train.TrainDto;
import uz.com.railway_reservation.model.dto.train.TrainForFront;
import uz.com.railway_reservation.model.entity.train.TrainEntity;
import uz.com.railway_reservation.model.entity.train.TrainStatus;
import uz.com.railway_reservation.model.entity.user.UserEntity;
import uz.com.railway_reservation.repository.TrainRepository;
import uz.com.railway_reservation.repository.UserRepository;
import uz.com.railway_reservation.response.StandardResponse;
import uz.com.railway_reservation.response.Status;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TrainService {
    private final ModelMapper modelMapper;
    private final TrainRepository trainRepository;
    private final UserRepository userRepository;

    public StandardResponse<TrainForFront> save(TrainDto trainDto, Principal principal){
        UserEntity userEntity = userRepository.findUserEntityByEmail(principal.getName());
        checkHasTrain(trainDto.getNumber());

        TrainEntity train = modelMapper.map(trainDto, TrainEntity.class);
        train.setTrainNumber(trainDto.getNumber());
        train.setCountOfWagon(trainDto.getCountOfWagon());
        train.setCreatedBy(userEntity.getId());
        train.setStatus(TrainStatus.ACTIVE);
        TrainEntity save = trainRepository.save(train);
        TrainForFront trainForFront = modelMapper.map(save, TrainForFront.class);
        return StandardResponse.<TrainForFront>builder()
                .status(Status.SUCCESS)
                .message("Train saved!")
                .data(trainForFront)
                .build();
    }

    public void checkHasTrain(String number){
        TrainEntity trainEntity = trainRepository.findTrainEntityByTrainNumber(number);
        if (trainEntity!=null){
            throw new DataHasAlreadyExistException("Train has already exist!");
        }
    }

    public StandardResponse<String> delete(String number, Principal principal){
        UserEntity user = userRepository.findUserEntityByEmail(principal.getName());
        TrainEntity train = trainRepository.findTrainEntityByTrainNumber(number);
        if (train==null){
            throw new DataNotFoundException("Unfortunately,train not found same this number!");
        }
        train.setDeleted(true);
        train.setDeletedBy(user.getId());
        train.setDeletedTime(LocalDateTime.now());
        train.setStatus(TrainStatus.NOT_WORK);
        trainRepository.save(train);
        return StandardResponse.<String>builder()
                .status(Status.SUCCESS)
                .message("Train deleted!")
                .data("DELETED!")
                .build();
    }
}
