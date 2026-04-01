package smart_canteen_backend.demo.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smart_canteen_backend.demo.dto.LoginRequestDto;
import smart_canteen_backend.demo.dto.RegisterRequestDto;
import smart_canteen_backend.demo.dto.UserDto;
import smart_canteen_backend.demo.service.UserService;

@RestController
@RequestMapping("/auth")

@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequestDto registerRequestDto) {
        return ResponseEntity.ok(userService.register(registerRequestDto));
    }
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(userService.login(loginRequestDto));

    }
}
