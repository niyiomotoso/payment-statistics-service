package com.n26.repositories.implementation;

import com.n26.models.Transaction;
import com.n26.persistence.TransactionConcurrentStore;
import com.n26.repositories.StatisticsRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class StatisticsRepoImplt implements StatisticsRepository {


    @Override
    public ArrayList<Transaction> getStatistics() {
         return TransactionConcurrentStore.getInstance().getAllTransactions();
    }
}
