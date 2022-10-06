package app.olxclone.controllers;

import app.olxclone.domain.User;
import app.olxclone.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{username}")
    public Mono<User> getUser(@PathVariable String username){
        return userService.findByUsername(username);
    }
}
