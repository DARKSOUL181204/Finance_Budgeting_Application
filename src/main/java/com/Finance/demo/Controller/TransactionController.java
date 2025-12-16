package com.Finance.demo.Controller;


import com.Finance.demo.DTO.TransactionDto;
import com.Finance.demo.DTO.UserDto;
import com.Finance.demo.Request.Transaction.CreateTransactionRequest;
import com.Finance.demo.Request.Transaction.UpdateTransactionRequest;
import com.Finance.demo.Response.ApiResponse;
import com.Finance.demo.Services.Transaction.ITransactionServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/transaction")
public class TransactionController {

    private final ITransactionServices transactionServices;

    @PostMapping("/create/{userId}")
    ResponseEntity<ApiResponse> createTransaction(@RequestBody CreateTransactionRequest request ,@PathVariable Long userId){
        TransactionDto transaction = transactionServices.createTransaction(request, userId);
        return ResponseEntity.ok(new ApiResponse("transaction Successfully created" , transaction));
    }

    @GetMapping("get/{Id}")
    ResponseEntity<ApiResponse> getTransactionById(@PathVariable Long Id){
        TransactionDto transaction = transactionServices.getTransactionById(Id);
        return ResponseEntity.ok(new ApiResponse("fetch Transaction Successfully " , transaction));
    }
    @GetMapping("/get/all/{userId}")
    ResponseEntity<ApiResponse>getAllTransactionsByUser(@PathVariable Long userId){
        List<TransactionDto> transactions = transactionServices.getAllTransactionsByUser(userId);
        return ResponseEntity.ok(new ApiResponse("fetch All Transaction Successfully " , transactions));
    }
    @PostMapping("/update/{Id}")
    ResponseEntity<ApiResponse>updateTransaction(@RequestBody UpdateTransactionRequest request ,@PathVariable Long Id){
        TransactionDto transaction = transactionServices.updateTransaction(request, Id);
        return ResponseEntity.ok(new ApiResponse("Updated Transaction Successfully " , transaction));
    }
    @GetMapping("/get/users/{Id}")
    ResponseEntity<ApiResponse>getUserByTransactionId(@PathVariable Long Id){
        UserDto user = transactionServices.getUserByTransactionId(Id);
        return ResponseEntity.ok(new ApiResponse("fetch User Successfully " , user));
    }
    @DeleteMapping("/delete/{Id}")
    ResponseEntity<ApiResponse>deleteTransaction(@PathVariable Long Id){
        transactionServices.deleteTransaction(Id);
        return ResponseEntity.ok(new ApiResponse("Deleted Transaction Successfully " , null));
    }





}
