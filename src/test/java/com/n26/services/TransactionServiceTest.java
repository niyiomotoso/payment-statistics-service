package com.n26.services;

import com.n26.models.Transaction;
import com.n26.repositories.TransactionRepository;
import com.n26.services.Implementation.TransactionServiceImplt;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImplt transactionServiceImplt;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void verifyCreateTransaction(){
        Date now = new Date();
        Transaction payload = new Transaction("400", now);
        when(transactionRepository.createNewTransaction(payload)).thenReturn(payload);
        Transaction response =  transactionServiceImplt.createNewTransaction(payload);
        verify(transactionRepository, times(1)).createNewTransaction(payload);
        Assert.assertEquals(response.getAmount(), "400");
        Assert.assertEquals(response.getTimestamp(), now);
    }

    @Test
    public void deleteAllTransactions(){
        when(transactionRepository.deleteAllTnansactions()).thenReturn(true);
        boolean result = transactionServiceImplt.deleteAllTnansactions();
        verify( transactionRepository, times(1) ).deleteAllTnansactions();
        Assert.assertEquals(true, result);
    }
}
