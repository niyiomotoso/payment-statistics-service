package com.n26.services;

import com.n26.models.Statistics;
import com.n26.models.Transaction;
import com.n26.repositories.StatisticsRepository;
import com.n26.services.Implementation.StatisticsServiceImplt;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class StatisticsServiceTest {

    @Mock
    StatisticsRepository statisticsRepository;

    @InjectMocks
    StatisticsServiceImplt statisticsServiceImplt;
   @Test
   public void verifygetStatistics(){
       ArrayList<Transaction> transactions = new ArrayList<Transaction>();

       Transaction validTransaction01 = new Transaction("200.4988", offsetCurrentDateTimeBySeconds(10)); //valid
       Transaction inValidTransaction01 = new Transaction("800.6785", offsetCurrentDateTimeBySeconds(90)); // invalid old transaction > 60s
       Transaction validTransaction02 = new Transaction("20.4615", offsetCurrentDateTimeBySeconds(05)); //valid
       Transaction inValidTransaction02 = new Transaction("2050.4115", offsetCurrentDateTimeBySeconds(65)); //invalid
       Transaction validTransaction03 = new Transaction("2040.4555", offsetCurrentDateTimeBySeconds(30)); //valid

       transactions.add(validTransaction01);
       transactions.add(inValidTransaction01);
       transactions.add(validTransaction02);
       transactions.add(inValidTransaction02);
       transactions.add(validTransaction03);


       when(statisticsRepository.getStatistics()).thenReturn(transactions);
       Statistics stat  = statisticsServiceImplt.getStatistics();
       Assert.assertEquals(stat.getCount(), 3); //valid transactions

       Assert.assertEquals(stat.getMin(), BigDecimal.valueOf(Double.parseDouble(validTransaction02.getAmount()))
               .setScale(2,RoundingMode.HALF_UP).toString());

       Assert.assertEquals(stat.getMax(), BigDecimal.valueOf(Double.parseDouble(validTransaction03.getAmount()))
               .setScale(2, RoundingMode.HALF_UP).toString());

       Assert.assertEquals(stat.getSum(), BigDecimal.valueOf( Double.parseDouble(validTransaction01.getAmount()) +
               Double.parseDouble(validTransaction02.getAmount())+ Double.parseDouble(validTransaction03.getAmount())
       ).setScale(2,RoundingMode.HALF_UP).toString());

       Assert.assertEquals(stat.getAvg(), BigDecimal.valueOf( (Double.parseDouble(validTransaction01.getAmount()) +
               Double.parseDouble(validTransaction02.getAmount())+ Double.parseDouble(validTransaction03.getAmount())) /3
       ).setScale(2,RoundingMode.HALF_UP).toString());


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
