package exercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

import exercise.daytime.Daytime;
import exercise.daytime.Day;
import exercise.daytime.Night;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.annotation.RequestScope;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @RequestScope
    public static Daytime getCurrentDayTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        if(localDateTime.getHour() > 6 && localDateTime.getHour() <= 22) {
            return new Day();
        } else {
            return new Night();
        }
    }
}
