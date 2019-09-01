package com.n26.services.Implementation;

import com.n26.models.Statistics;
import com.n26.models.Transaction;
import com.n26.repositories.StatisticsRepository;
import com.n26.services.StatisticsService;
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
public class StatisticsServiceImplt implements StatisticsService {
    private final Logger logger = LoggerFactory.getLogger(StatisticsServiceImplt.class);

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Override
    public Statistics getStatistics() {
        ArrayList<Transaction> transactions = statisticsRepository.getStatistics();
        ListIterator<Transaction> iterator = transactions.listIterator();
        Statistics new_stat = new Statistics();
        Double maxAmount = 0.000;
        Double minAmount = 0.000;
        try{
            minAmount = Double.valueOf(transactions.get(0).getAmount());
        }
        catch(IndexOutOfBoundsException ex){
            logger.info("no index 0");
        }

        Double avg = 0.000;
        Double  sum = 0.000 ;
        long count = 0;
        while (iterator.hasNext() ){

            Transaction transaction = iterator.next();

            //average amount of transaction value in the last 60 seconds
            if( PayloadValidator.timeDiffFromNowCalculator(transaction.getTimestamp()) < 60){
                Double amount = Double.parseDouble(transaction.getAmount());
                if(amount < minAmount)
                    minAmount = amount;
                if(amount > maxAmount)
                    maxAmount = amount;
                sum = sum + amount;

                count ++;
            }
        }
        if(count != 0)
            avg = sum/count;
        else
            avg = 0.000;

        new_stat.setMax( BigDecimal.valueOf(maxAmount).setScale(2, RoundingMode.HALF_UP).toString());
        new_stat.setMin(BigDecimal.valueOf(minAmount).setScale(2, RoundingMode.HALF_UP).toString());
        new_stat.setSum(BigDecimal.valueOf(sum).setScale(2, RoundingMode.HALF_UP).toString());
        new_stat.setAvg(BigDecimal.valueOf(avg).setScale(2, RoundingMode.HALF_UP).toString());
        new_stat.setCount(count);

        return new_stat;
    }
}
