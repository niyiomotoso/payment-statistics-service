package com.n26.controllers;

import com.n26.models.Statistics;
import com.n26.services.StatisticsService;
import com.n26.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/statistics")
    public ResponseEntity<Statistics> getAllTransactions(){
        Statistics stats =  statisticsService.getStatistics();
        return new ResponseEntity<Statistics>(stats, HttpStatus.OK);
    }
}
