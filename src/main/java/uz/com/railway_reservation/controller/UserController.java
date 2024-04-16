package uz.com.railway_reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.com.railway_reservation.model.dto.user.UserForFront;
import uz.com.railway_reservation.model.entity.user.UserEntity;
import uz.com.railway_reservation.response.StandardResponse;
import uz.com.railway_reservation.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<String> delete(
            @PathVariable UUID id,
            Principal principal
            ){
      return userService.delete(id, principal);
    }

    @PostMapping("/get-by-email")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<UserForFront> getByEmail(
            @RequestParam String email
    ){
        return userService.getByEmail(email);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<UserForFront> getById(
            @PathVariable UUID id
    ){
        return userService.getById(id);
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserEntity> getAll(){
        return userService.getAll();
    }

    @PostMapping("/get-by-number")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<UserForFront> getByNumber(
            @RequestParam String number
    ){
        return userService.getByNumber(number);
    }
}
