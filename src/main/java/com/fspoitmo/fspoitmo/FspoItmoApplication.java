package com.fspoitmo.fspoitmo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FspoItmoApplication {

    public static Long SYNC_TIME = System.currentTimeMillis()/1000;
    public static String CURRENT_WEEK = "odd";

    static final Logger log =  LoggerFactory.getLogger(FspoItmoApplication.class);

    public static Logger getLog() {
        return log;
    }



    public static void main(String[] args) {
        SpringApplication.run(FspoItmoApplication.class, args);
    }
}
