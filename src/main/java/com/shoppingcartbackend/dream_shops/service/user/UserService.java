package com.shoppingcartbackend.dream_shops.service.user;

import com.shoppingcartbackend.dream_shops.dto.UserDto;
import com.shoppingcartbackend.dream_shops.exception.AlreadyExistsException;
import com.shoppingcartbackend.dream_shops.exception.ResourceNotFoundException;
import com.shoppingcartbackend.dream_shops.model.User;
import com.shoppingcartbackend.dream_shops.repository.UserRepository;
import com.shoppingcartbackend.dream_shops.request.CreateUserRequest;
import com.shoppingcartbackend.dream_shops.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("User Not Found!"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request).filter(user-> !userRepository.existsByEmail(request.getEmail()))
                .map(req-> {
                    User user = new User();
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    return userRepository.save(user);
                }).orElseThrow(()-> new AlreadyExistsException("OOPS! " +request.getEmail() + " Already Exists!"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser ->{
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(()-> new ResourceNotFoundException("User Not Found!"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository :: delete,
                ()-> { throw new ResourceNotFoundException("User Not Found!");
    });
    }

    @Override
    public UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
