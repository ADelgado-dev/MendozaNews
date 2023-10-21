package com.mendozanews.apinews;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController 
@SpringBootApplication
public class ApiNewsApplication {

//  @RequestMapping(value = "/hello", produces = "text/plain", method = RequestMethod.GET, consumes = "text/plain")
@GetMapping 

 public String hello() {
        return "Hello, world!";
    }
    public static void main(String[] args) {
        SpringApplication.run(ApiNewsApplication.class, args);
    }

}
