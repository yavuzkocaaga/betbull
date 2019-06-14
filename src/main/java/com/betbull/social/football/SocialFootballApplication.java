package com.betbull.social.football;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SocialFootballApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialFootballApplication.class, args);
    }

}
