package com.n26.controllers;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TransactionControllerTest {

    @Autowired
    WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    //422 – if the transaction date is in the future
    @Test
    public void testAFutureTimeTransacton() throws Exception{

        String futureDateString = offsetCurrentTimeBySeconds(-200);
        System.out.println("future "+ futureDateString);
        String contentString = "{ \"amount\": 850, \"timestamp\": \""+futureDateString+"\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/transactions").contentType(MediaType.APPLICATION_JSON).
                content(contentString)).andExpect(status().isUnprocessableEntity()).andExpect(content().bytes(new byte[0]));
    }

    //422 – if timestamp is not parsable
    @Test
    public void testAnInvalidTimeTransacton() throws Exception{

        String contentString = "{ \"amount\": 850, \"timestamp\": \"201-201-09-01T16:24:14.312Z\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/transactions").contentType(MediaType.APPLICATION_JSON).
                content(contentString)).andExpect(status().isUnprocessableEntity()).andExpect(content().bytes(new byte[0]));
    }

    //422 – if amount is not parsable
    @Test
    public void testAnInvalidAmountTransacton() throws Exception{

        String validDateString = offsetCurrentTimeBySeconds(20);
        String contentString = "{ \"amount\": \"Fifty\", \"timestamp\": \""+validDateString+ "\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/transactions").contentType(MediaType.APPLICATION_JSON).
                content(contentString)).andExpect(status().isUnprocessableEntity()).andExpect(content().bytes(new byte[0]));
    }

    //400 – if the JSON is invalid
    @Test
    public void testAnInvalidJSONTransacton() throws Exception{

        String validDateString = offsetCurrentTimeBySeconds(10);
        String contentString = " \"amount\": 850 \"timestamp\": \""+validDateString+ "\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/transactions").contentType(MediaType.APPLICATION_JSON).
                content(contentString)).andExpect(status().isBadRequest()).andExpect(content().bytes(new byte[0]));
    }

    //204 if the transaction is older than 60 seconds
    @Test
    public void testAnOldTimeTransacton() throws Exception{


        String invalidDateString = offsetCurrentTimeBySeconds(90);
        String contentString = "{ \"amount\": 850, \"timestamp\": \""+invalidDateString+ "\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/transactions").contentType(MediaType.APPLICATION_JSON).
                content(contentString)).andExpect(status().isNoContent()).andExpect(content().bytes(new byte[0]));;
    }

    //201 – in case of success
    @Test
    public void testAnAllRoundValidTransacton() throws Exception{

        String validDateString = offsetCurrentTimeBySeconds(30);
            String contentString = "{ \"amount\": 850, \"timestamp\": \""+validDateString+ "\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/transactions").contentType(MediaType.APPLICATION_JSON).
                content(contentString)).andExpect(status().isCreated()).andExpect(content().bytes(new byte[0]));;
    }

    //204 – delete all transactions
    @Test
    public void testDeleteTransactons() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.delete("/transactions").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent()).andExpect(content().bytes(new byte[0]));;
    }


    private String offsetCurrentTimeBySeconds(int offset){
        Date nowTimeStamp = new Date(System.currentTimeMillis());
        long offstInMilliseconds = offset * 1000;
        long currentTimeLessOffset =  nowTimeStamp.getTime() - offstInMilliseconds;
        String isoDatePattern = "yyyy-MM-dd'T'HH:mm:ss.sssZ";
        SimpleDateFormat dateFormat = new SimpleDateFormat(isoDatePattern);
        Date date = new Date(currentTimeLessOffset);
        String offsetdDateString = dateFormat.format(date);
        return offsetdDateString;
    }

}
