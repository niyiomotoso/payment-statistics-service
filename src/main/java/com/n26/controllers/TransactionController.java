package com.n26.controllers;

import com.n26.exceptions.TransactionException;
import com.n26.models.Statistics;
import com.n26.models.Transaction;
import com.n26.services.TransactionService;
import com.n26.utils.PayloadValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transactions")
    public ResponseEntity createNewTransaction(@RequestBody Transaction payload) throws TransactionException {
        PayloadValidator.validateCreatePayload(payload);
        Transaction response = transactionService.createNewTransaction(payload);
        return new ResponseEntity(HttpStatus.CREATED);
    }


    @DeleteMapping("/transactions")
    public ResponseEntity deleteTransaction(){
        transactionService.deleteAllTnansactions();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

