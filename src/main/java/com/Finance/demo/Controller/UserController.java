package com.Finance.demo.Controller;
import com.Finance.demo.DTO.UserDto;
import com.Finance.demo.DTO.WalletDto;
import com.Finance.demo.Model.User;
import com.Finance.demo.Model.Wallet;
import com.Finance.demo.Request.User.CreateUserRequest;
import com.Finance.demo.Request.User.UpdateUserRequest;
import com.Finance.demo.Response.ApiResponse;
import com.Finance.demo.Services.User.IUserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users") // Lowercase URL is standard
public class UserController {

    private final IUserServices userService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findUserById(@PathVariable Long id) {
        UserDto user = userService.findUserById(id);
        return ResponseEntity.ok(new ApiResponse("User found", user));
    }

    // NOTE: Use @RequestParam for GET, not @RequestBody
    @GetMapping("/by-email")
    public ResponseEntity<ApiResponse> getUserByEmail(@RequestParam String email) {
        UserDto user = userService.getUserByEmail(email);
        return ResponseEntity.ok(new ApiResponse("User found", user));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {
        User user = userService.createUser(request);
        return ResponseEntity.ok(new ApiResponse("User created successfully", user));
    }

    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long Id){
        userService.deleteUser(Id);
        return ResponseEntity.ok(new ApiResponse("User Deleted successfully", null));
    }

    @GetMapping("/all/wallets")
    public ResponseEntity<ApiResponse>  getWallets(@RequestParam Long Id){
        List<WalletDto> wallets = userService.getWallets(Id);
        return ResponseEntity.ok(new ApiResponse("Get User wallet successfully", wallets));
    }

    @GetMapping("/all/users")
    public ResponseEntity<ApiResponse>  getAllUsers(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse("Get User wallet successfully", users));
    }

    @PostMapping("/update/{Id}")
    public ResponseEntity<ApiResponse> updateUser( @RequestBody UpdateUserRequest request, @PathVariable Long Id){
        UserDto user = userService.updateUser(request,Id);
        return ResponseEntity.ok(new ApiResponse("User updated successfully", user));
    }


}