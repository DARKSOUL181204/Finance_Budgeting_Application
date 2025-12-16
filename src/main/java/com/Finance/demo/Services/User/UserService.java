package com.Finance.demo.Services.User;

import com.Finance.demo.DTO.UserDto;
import com.Finance.demo.DTO.WalletDto;
import com.Finance.demo.Exceptions.AlreadyExistException;
import com.Finance.demo.Exceptions.ResourceDoesNotExistException;
import com.Finance.demo.Exceptions.ResourceNotFoundException;
import com.Finance.demo.Model.User;
import com.Finance.demo.Repository.UserRepository;
import com.Finance.demo.Request.User.CreateUserRequest;
import com.Finance.demo.Request.User.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserServices {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ModelMapper modelMapper;
    @Override
    public UserDto findUserById(Long Id) {
        User user =  userRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("not Found"));
       return modelMapper.map(user,UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
       User user =  userRepository
               .findByEmail(email)
               .orElseThrow(()-> new ResourceNotFoundException("not Found"));
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public User createUser(CreateUserRequest request) {
       return  Optional.ofNullable(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setFirstName(req.getFirstName());
                    user.setLastName(req.getLastName());
                    user.setEmail(req.getEmail());
                    user.setPassword(req.getPassword());
                    return userRepository.save(user);
                })
                .orElseThrow(
                        () -> new AlreadyExistException("Oops !!  already Exist"));

    }

    @Override
    public void deleteUser(Long Id) {
        if (!userRepository.existsById(Id)) {
            throw new ResourceDoesNotExistException("User id does not exist: " + Id) ;
        }
    userRepository.deleteById(Id);
    }

    // pending
    @Override
    public UserDto updateUser(UpdateUserRequest request, Long Id) {
        User user = userRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("user not found"));
        if(request.getFirstName() != null && !request.getFirstName().isEmpty()){
            user.setFirstName(request.getFirstName());
        }
        if(request.getLastName() != null && !request.getLastName().isEmpty()){
            user.setLastName(request.getLastName());
        }
        userRepository.save(user);
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public List<WalletDto> getWallets(Long Id) {
        User user = userRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));
        return user.getWallets()
                .stream() // 1. Turn list into a stream
                .map(wallet -> modelMapper.map(wallet, WalletDto.class)) // 2. Convert each wallet
                .collect(Collectors.toList()); // 3. Collect back into a list
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
