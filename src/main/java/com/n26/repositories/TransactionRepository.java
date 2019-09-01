package com.n26.repositories;

import com.n26.models.Transaction;

import java.util.ArrayList;

public interface TransactionRepository {
    public Transaction createNewTransaction(Transaction payload);
    public boolean deleteAllTnansactions();
}
