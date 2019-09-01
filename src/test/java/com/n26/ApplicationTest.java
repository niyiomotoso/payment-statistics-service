package com.n26;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest{

    public static void main (String...args){

        SpringApplication.run(ApplicationTest.class, args);

    }

    @Test
    public void contextLoads(){

    }
}
