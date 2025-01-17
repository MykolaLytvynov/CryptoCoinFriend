package ua.mykola.cryptocoinfriend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class CryptoCoinFriend {

    public static void main(String[] args) {
        SpringApplication.run(CryptoCoinFriend.class, args);
    }
}
