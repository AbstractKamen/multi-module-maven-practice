package com.abstractkamen;

import com.abstractkamen.controllers.UserController;
import com.abstractkamen.repositories.UserRepository;
import com.abstractkamen.xsddomain.UserCommand;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@SpringBootApplication
@ComponentScan(value = {"com.abstractkamen.controllers",
    "com.abstractkamen.mappers"
})
@EntityScan(value = "com.abstractkamen.entities")
public class Main {
    public static void main(String[] args) {
        UserCommand userCommand = new UserCommand();
        userCommand.setEmail("puttingus@gus.com");
        userCommand.setFirstName("VladimirGus");
        userCommand.setLastName("PutinGus");
        final ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        context.getBean(UserController.class).saveUser(userCommand);
        context.getBean(UserRepository.class).findAll().forEach(System.out::println);

    }
}
