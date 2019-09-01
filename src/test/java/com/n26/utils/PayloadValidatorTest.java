package com.n26.utils;

import com.n26.exceptions.TransactionException;
import com.n26.models.Transaction;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
public class PayloadValidatorTest {
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void testInvalidAmount() throws TransactionException {
        expectedEx.expect(TransactionException.class);
        expectedEx.expectMessage("422");
        Transaction transaction = new Transaction("Niyi", offsetCurrentDateTimeBySeconds(10));
        PayloadValidator.validateCreatePayload(transaction);


    }

    @Test
    public void testIfTransactionIsOlderThan60Seconds() throws TransactionException {
        expectedEx.expect(TransactionException.class);
        expectedEx.expectMessage("204");
        Transaction transaction = new Transaction("500.5005", offsetCurrentDateTimeBySeconds(70));
        PayloadValidator.validateCreatePayload(transaction);

    }

    @Test
    public void testIfTransactionIsInTheFuture() throws TransactionException {
        expectedEx.expect(TransactionException.class);
        expectedEx.expectMessage("422");
        Transaction transaction = new Transaction("45.5544", offsetCurrentDateTimeBySeconds(-70));
        PayloadValidator.validateCreatePayload(transaction);


    }





    private Date offsetCurrentDateTimeBySeconds(int offset){
        Date nowTimeStamp = new Date(System.currentTimeMillis());
        long offstInMilliseconds = offset * 1000;
        long currentTimeLessOffset =  nowTimeStamp.getTime() - offstInMilliseconds;
        String isoDatePattern = "yyyy-MM-dd'T'HH:mm:ss.sssZ";
        SimpleDateFormat dateFormat = new SimpleDateFormat(isoDatePattern);
        Date date = new Date(currentTimeLessOffset);
        return date;
    }

}
