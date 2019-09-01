package com.n26.persistence;

import com.n26.models.Transaction;

import java.util.ArrayList;


public class TransactionConcurrentStore  {

    private ArrayList<Transaction> transactions = new ArrayList<>();

    private TransactionConcurrentStore() {
    }

    private static class TransactionStoreLoader {
         static final TransactionConcurrentStore STORE_INSTANCE = new TransactionConcurrentStore();
    }

    public static TransactionConcurrentStore getInstance(){
           return TransactionStoreLoader.STORE_INSTANCE;
        }



    public boolean saveNewTransaction(Transaction payload){

        return transactions.add(payload);
    }


    public ArrayList<Transaction> getAllTransactions(){

        return transactions;
    }


    public boolean deleteTransaction(){
        transactions.clear();
        return true;
    }

}
