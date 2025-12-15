package com.Finance.demo.Services.Transaction;

import com.Finance.demo.DTO.TransactionDto;
import com.Finance.demo.DTO.UserDto;
import com.Finance.demo.Model.Transaction;
import com.Finance.demo.Model.User;
import com.Finance.demo.Request.Transaction.CreateTransactionRequest;
import com.Finance.demo.Request.Transaction.UpdateTransactionRequest;

import java.util.List;

public interface ITransactionServices {
    Transaction createTransaction(CreateTransactionRequest request , Long userId);
    TransactionDto getTransactionById(Long Id);
    List<TransactionDto> getAllTransactionsByUser(Long userId);
    TransactionDto updateTransaction(UpdateTransactionRequest request , Long Id);
    UserDto getUserByTransactionId(Long Id);
    void deleteTransaction(Long Id);
}
