package exercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

    @GetMapping("/about")
    String hello() {
        return "Welcome to Hexlet!";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
