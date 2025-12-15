package com.Finance.demo.Services.Wallet;

import com.Finance.demo.DTO.UserDto;
import com.Finance.demo.DTO.WalletDto;
import com.Finance.demo.Model.User;
import com.Finance.demo.Model.Wallet;
import com.Finance.demo.Request.Wallet.CreateWalletRequest;
import com.Finance.demo.Request.Wallet.UpdateWalletRequest;

import java.math.BigDecimal;

public interface IWalletServices {
    WalletDto findWalletById(Long Id);
    WalletDto getWalletByAccountNumber(String accountNumber);
    BigDecimal getBalanceById(Long Id);
    UserDto getUserByWalletId(Long Id);
    WalletDto createWallet(CreateWalletRequest request);
    void deleteWalletById(Long Id);
    WalletDto updateWallet(UpdateWalletRequest request,Long Id);

}
