package com.example.demo.utilities;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class Utilitarios {

    public Timestamp getTimestamp(){
        long currenTIme = System.currentTimeMillis();
        return new Timestamp(currenTIme);
    }

}
