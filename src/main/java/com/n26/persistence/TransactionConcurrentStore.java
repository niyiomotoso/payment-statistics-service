package com.n26.persistence;

import com.n26.models.Statistics;
import com.n26.models.Transaction;
import com.n26.utils.PayloadValidator;

import java.util.ArrayList;
import java.util.ListIterator;


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
        boolean added = transactions.add(payload);

        new Thread(){
            public void run(){
                System.out.println("Thread Running");
                ListIterator<Transaction> iterator = transactions.listIterator();

                while (iterator.hasNext() ){
                    Transaction transaction = iterator.next();

                    //remove transactions older than 60 seconds
                    if( PayloadValidator.timeDiffFromNowCalculator(transaction.getTimestamp()) >= 60){
                        iterator.remove();
                        System.out.println("removed ");

                    }
                }
            }
        }.start();

        return added;
    }


    public ArrayList<Transaction> getAllTransactions(){

        return transactions;
    }


    public boolean deleteTransaction(){
        transactions.clear();
        return true;
    }

}
