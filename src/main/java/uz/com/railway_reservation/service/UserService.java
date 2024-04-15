package uz.com.railway_reservation.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.com.railway_reservation.exception.AuthenticationFailedException;
import uz.com.railway_reservation.exception.DataNotFoundException;
import uz.com.railway_reservation.exception.UserBadRequestException;
import uz.com.railway_reservation.model.dto.user.LoginDto;
import uz.com.railway_reservation.model.dto.user.UserDto;
import uz.com.railway_reservation.model.dto.user.UserForFront;
import uz.com.railway_reservation.model.entity.user.Gender;
import uz.com.railway_reservation.model.entity.user.UserEntity;
import uz.com.railway_reservation.model.entity.user.UserRole;
import uz.com.railway_reservation.repository.UserRepository;
import uz.com.railway_reservation.response.JwtResponse;
import uz.com.railway_reservation.response.StandardResponse;
import uz.com.railway_reservation.response.Status;
import uz.com.railway_reservation.service.auth.JwtService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public StandardResponse<JwtResponse> signUp(UserDto userDto){
        checkUserEmailAndPhoneNumber(userDto.getEmail(),userDto.getNumber());
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setRole(UserRole.USER);
        userEntity.setFullName(userDto.getFullName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setGender(Gender.valueOf(userDto.getGender()));
        userEntity.setNumber(userDto.getNumber());
        userEntity=userRepository.save(userEntity);
        userEntity.setCreatedBy(userEntity.getId());
        userRepository.save(userEntity);
        String accessToken = jwtService.generateAccessToken(userEntity);
        String refreshToken = jwtService.generateRefreshToken(userEntity);
        UserForFront user = modelMapper.map(userEntity, UserForFront.class);
        JwtResponse jwtResponse = JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(user)
                .build();
        return StandardResponse.<JwtResponse>builder()
                .status(Status.SUCCESS)
                .message("Successfully signed Up")
                .data(jwtResponse)
                .build();
    }
    private void checkUserEmailAndPhoneNumber(String email, String phoneNumber) {
        UserEntity userEntity = userRepository.findUserEntityByEmail(email);
        if (userEntity!=null){
            throw new UserBadRequestException("Email has already exist!");
        }
        if (userRepository.findUserEntityByNumber(phoneNumber).isPresent()){
            throw new UserBadRequestException("Number has already exist!");
        }
    }

    public StandardResponse<JwtResponse> login(LoginDto loginDto){
        UserEntity userEntity = userRepository.findUserEntityByEmail(loginDto.getEmail());
        if (userEntity == null){
            throw new DataNotFoundException("User not found!");
        }
        if (passwordEncoder.matches(loginDto.getPassword(), userEntity.getPassword())){
            String accessToken= jwtService.generateAccessToken(userEntity);
            String refreshToken= jwtService.generateRefreshToken(userEntity);
            UserForFront user = modelMapper.map(userEntity, UserForFront.class);
            JwtResponse jwtResponse= JwtResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .user(user)
                    .build();
            return StandardResponse.<JwtResponse>builder()
                    .status(Status.SUCCESS)
                    .message("Sign in successfully!")
                    .data(jwtResponse)
                    .build();
        }
        else{
            throw new AuthenticationFailedException("Something error during signed in!");
        }
    }
    public StandardResponse<String> delete(UUID id, Principal principal){
        UserEntity user = userRepository.findUserEntityByEmail(principal.getName());
        UserEntity userEntity = userRepository.findUserEntityById(id);
        if (userEntity==null){
            throw new DataNotFoundException("User not found!");
        }
        userEntity.setDeleted(true);
        userEntity.setDeletedBy(user.getId());
        userEntity.setDeletedTime(LocalDateTime.now());
        userRepository.save(userEntity);
        return StandardResponse.<String>builder()
                .data("User deleted successfully!")
                .status(Status.SUCCESS)
                .message("DELETED")
                .build();
    }

    public StandardResponse<UserForFront> getByEmail(String email){
        UserEntity userEntity = userRepository.findUserEntityByEmail(email);
        if (userEntity==null){
            throw new DataNotFoundException("User not found!");
        }
        UserForFront user = modelMapper.map(userEntity, UserForFront.class);
        return StandardResponse.<UserForFront>builder()
                .status(Status.SUCCESS)
                .data(user)
                .message("This is user")
                .build();
    }


    public StandardResponse<UserForFront> getById(UUID id){
        UserEntity userEntity = userRepository.findUserEntityById(id);
        if (userEntity==null){
            throw new DataNotFoundException("User not found!");
        }
        UserForFront user = modelMapper.map(userEntity, UserForFront.class);
        return StandardResponse.<UserForFront>builder()
                .status(Status.SUCCESS)
                .data(user)
                .message("This is user")
                .build();
    }
}
