package com.n26.repositories.implementation;

import com.n26.models.Transaction;
import com.n26.persistence.TransactionConcurrentStore;
import com.n26.repositories.TransactionRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepoImplt implements TransactionRepository {

    public Transaction createNewTransaction(Transaction payload){

        TransactionConcurrentStore.getInstance().saveNewTransaction(payload);

        return payload;
    }



    @Override
    public boolean deleteAllTnansactions() {
        TransactionConcurrentStore.getInstance().deleteTransaction();
        return true;
    }

}
