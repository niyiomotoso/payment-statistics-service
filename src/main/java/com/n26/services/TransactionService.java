package com.n26.services;

import com.n26.models.Statistics;
import com.n26.models.Transaction;

import java.util.ArrayList;

public interface TransactionService {

    public Transaction createNewTransaction(Transaction payload);
    public boolean deleteAllTnansactions();
}
