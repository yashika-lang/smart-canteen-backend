package smart_canteen_backend.demo.service;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import smart_canteen_backend.demo.dto.LoginRequestDto;
import smart_canteen_backend.demo.dto.RegisterRequestDto;
import smart_canteen_backend.demo.dto.UserDto;
import smart_canteen_backend.demo.entity.User;
import smart_canteen_backend.demo.repository.UserRepository;
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Override // register the user
    public UserDto register(RegisterRequestDto registerRequestDto) {

        User user = new User();

        user.setName(registerRequestDto.getName());
        user.setEmail(registerRequestDto.getEmail());
        user.setPassword(registerRequestDto.getPassword());
        user.setRole("USER");

        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override // login user by their mail
    public UserDto login(LoginRequestDto dto) {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(dto.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return modelMapper.map(user, UserDto.class);
    }
}
