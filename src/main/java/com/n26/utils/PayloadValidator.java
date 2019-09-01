package com.n26.utils;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.n26.exceptions.TransactionException;
import com.n26.models.Transaction;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class PayloadValidator {
    public static boolean validateCreatePayload(Transaction payload) throws TransactionException {

        //check for timestamp and amoount as part of the payload
        if(payload.getTimestamp() == null || payload.getAmount() == null) {
            throw new TransactionException("422");
        }


        //parse timestamp



        long timeDifferenceInSeconds = timeDiffFromNowCalculator(payload.getTimestamp());

        //204 – if the transaction is older than 60 seconds
        if(timeDifferenceInSeconds >= 60)
            throw new TransactionException("204");
        //422 – if the transaction date is in the future
        else if(timeDifferenceInSeconds < 0)
            throw new TransactionException("422");


        //parse Amount
        try{
            Double amount = Double.valueOf(payload.getAmount());

        }catch (NumberFormatException e){
            throw new TransactionException("422");
        }


        return true;
    }

    public static long timeDiffFromNowCalculator(Date reference){


        Date nowTimeStamp = new Date();
//        System.out.println("now "+nowTimeStamp);
//        System.out.println("payload "+reference);
//        System.out.println("now "+nowTimeStamp.getTime());
//        System.out.println("payload "+reference.getTime());
        long timeDifferenceInMiliSeconds = nowTimeStamp.getTime() - reference.getTime();

        long timeDifferenceInSeconds = timeDifferenceInMiliSeconds/1000;
        return timeDifferenceInSeconds;

    }
}
