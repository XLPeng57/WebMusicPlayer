package com.example.musicserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@MapperScan("com.example.musicserver.mapper")
public class MusicServerApplication {



    public static void main(String[] args) {
        SpringApplication.run(MusicServerApplication.class, args);
    }

    @GetMapping
    public String hello() {
        return "Hello World";
    }

}
