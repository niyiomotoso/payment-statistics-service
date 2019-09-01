package com.n26.services.Implementation;


import com.n26.models.Statistics;
import com.n26.models.Transaction;
import com.n26.repositories.TransactionRepository;
import com.n26.services.TransactionService;
import com.n26.utils.PayloadValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.ListIterator;

@Service
public class TransactionServiceImplt implements TransactionService {
    private final Logger logger = LoggerFactory.getLogger(TransactionServiceImplt.class);

    @Autowired
    private TransactionRepository transactionRepository;


    public Transaction createNewTransaction(Transaction payload){
        return transactionRepository.createNewTransaction(payload);
    }



    @Override
    public boolean deleteAllTnansactions() {
        return transactionRepository.deleteAllTnansactions();
    }
}

