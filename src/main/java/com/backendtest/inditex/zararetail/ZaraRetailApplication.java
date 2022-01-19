package com.backendtest.inditex.zararetail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZaraRetailApplication {

    private static final Logger log = LoggerFactory.getLogger(ZaraRetailApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ZaraRetailApplication.class, args);
    }

}
