package com.learn.elsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liyuhui
 */
@SpringBootApplication
public class ElsearchApplication {

    public static void main(String[] args) {
        System.out.println("before--------------------------------------------");
        SpringApplication.run(ElsearchApplication.class, args);
        System.out.println("success-------------------------------------------");
    }

}
