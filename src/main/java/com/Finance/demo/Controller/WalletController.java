package com.Finance.demo.Controller;

import com.Finance.demo.DTO.UserDto;
import com.Finance.demo.DTO.WalletDto;
import com.Finance.demo.Model.User;
import com.Finance.demo.Model.Wallet;
import com.Finance.demo.Request.Wallet.CreateWalletRequest;
import com.Finance.demo.Request.Wallet.UpdateWalletRequest;
import com.Finance.demo.Response.ApiResponse;
import com.Finance.demo.Services.Wallet.IWalletServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/wallet")
public class WalletController {
    @Autowired
    private final IWalletServices walletServices;


    @GetMapping("/id/{Id}")
    public ResponseEntity<ApiResponse> findWalletById(@PathVariable Long Id){
        WalletDto wallet = walletServices.findWalletById(Id);
        return ResponseEntity.ok(new ApiResponse("Wallet found Successfully " , wallet));
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<ApiResponse> getWalletByAccountNumber(@PathVariable String accountNumber){
        WalletDto wallet = walletServices.getWalletByAccountNumber(accountNumber);
        return ResponseEntity.ok(new ApiResponse("Wallet found Successfully " , wallet));
    }

    @GetMapping("/balance/{Id}")
    public ResponseEntity<ApiResponse> getBalanceById(@PathVariable Long Id){
        BigDecimal balance = walletServices.getBalanceById(Id);
        return ResponseEntity.ok(new ApiResponse("Wallet found Successfully " , balance));
    }

    @GetMapping("/User/wallet/{Id}")
    public ResponseEntity<ApiResponse> getUserByWalletId(@PathVariable Long Id){
        UserDto user = walletServices.getUserByWalletId(Id);
        return  ResponseEntity.ok(new ApiResponse("User found Successfully " , user));

    }
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createWallet(@RequestBody CreateWalletRequest request){
        WalletDto wallet = walletServices.createWallet(request);
        return  ResponseEntity.ok(new ApiResponse("Wallet Created Successfully " , wallet));
    }
    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<ApiResponse>deleteWalletById(@PathVariable Long Id){
        walletServices.deleteWalletById(Id);
        return  ResponseEntity.ok(new ApiResponse("Wallet Successfully Deleted " , null));
    }

    @PutMapping("/update/{Id}")
    public ResponseEntity<ApiResponse>updateWallet(@RequestBody UpdateWalletRequest request,@PathVariable Long Id){
        WalletDto wallet = walletServices.updateWallet(request, Id);
        return  ResponseEntity.ok(new ApiResponse("Wallet Successfully updated " , wallet));
    }
}
