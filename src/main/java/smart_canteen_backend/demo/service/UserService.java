package smart_canteen_backend.demo.service;

import org.springframework.stereotype.Service;
import smart_canteen_backend.demo.dto.LoginRequestDto;
import smart_canteen_backend.demo.dto.RegisterRequestDto;
import smart_canteen_backend.demo.dto.UserDto;


public interface UserService {


    UserDto register(RegisterRequestDto registerRequestDto);

    UserDto login(LoginRequestDto loginRequestDto);
}
