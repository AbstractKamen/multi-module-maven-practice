package com.abstractkamen.controllers;

import com.abstractkamen.entities.User;
import com.abstractkamen.mappers.api.GenericMapper;
import com.abstractkamen.xsddomain.UserCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final CrudRepository<User, Long> userRepository;
    private final GenericMapper<User, UserCommand> userMapper;

    @Autowired
    public UserController(CrudRepository<User, Long> userRepository,
                          GenericMapper<User, UserCommand> userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<String> controllerIsWorking() {
        return ResponseEntity.ok("User controller is working...");
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        final User user = userRepository.findById(Long.parseLong(id)).orElseThrow(RuntimeException::new);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestBody UserCommand userCommand) {
        final User user = userMapper.fromDto(userCommand);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
}
