package com.Finance.demo.Services.User;

import com.Finance.demo.DTO.UserDto;
import com.Finance.demo.DTO.WalletDto;
import com.Finance.demo.Model.User;
import com.Finance.demo.Model.Wallet;
import com.Finance.demo.Request.User.CreateUserRequest;
import com.Finance.demo.Request.User.UpdateUserRequest;

import java.util.List;

public interface IUserServices {
    UserDto findUserById(Long Id);
    UserDto getUserByEmail(String email);
    User createUser(CreateUserRequest request);
    void deleteUser(Long Id);
    UserDto updateUser(UpdateUserRequest request,Long Id);
    List<WalletDto> getWallets(Long Id);
    List<User> getAllUsers();

}
