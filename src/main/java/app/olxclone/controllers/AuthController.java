package app.olxclone.controllers;

import app.olxclone.Util.JWTUtil;
import app.olxclone.domain.AuthRequest;
import app.olxclone.domain.Role;
import app.olxclone.domain.User;
import app.olxclone.services.UserService;
import ch.qos.logback.core.util.Duration;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "Authorization", allowCredentials = "true")
public class AuthController {
    private final JWTUtil jwtUtil;
    private final UserService userService;

    @Value("${cookies.domain}")
    private String domain;

    public AuthController(JWTUtil jwtUtil, UserService userService){
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity> login(Mono<AuthRequest> authRequest) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return authRequest.flatMap(login -> {
            return userService.findByUsername(login.getUsername())
                    .filter(userDetails -> passwordEncoder.matches(login.getPassword(), userDetails.getPassword()))
                    .map(userDetails -> {
                        String token = jwtUtil.generateToken(userDetails);

                        ResponseCookie cookie = ResponseCookie.from("jwt", token)
                                .domain("127.0.0.1")
                                .path("/")
                                .maxAge(Duration.buildByDays(365).getMilliseconds())
                                .build();
                        HttpHeaders httpHeaders = new HttpHeaders();
                        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
                        httpHeaders.add(HttpHeaders.SET_COOKIE, cookie.toString());

                        return ResponseEntity.ok().headers(httpHeaders).body(token);
                    })
                    .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
        });
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validate(@CookieValue(name = "jwt") String token){
        try {
            Boolean isValid = jwtUtil.validateToken(token);
            return ResponseEntity.ok(isValid);
        }catch (ExpiredJwtException e){
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(){
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .domain(domain)
                .path("/")
                .maxAge(0)
                .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("ok");
    }

    @GetMapping("/checkUsername/{username}")
    public Mono<ResponseEntity<Boolean>> checkUsername(@PathVariable String username){
        Mono<ResponseEntity<Boolean>> responseEntityMono = userService.existsByUsername(username)
                .map(x -> new ResponseEntity<>(x, HttpStatus.OK));

        return responseEntityMono;
    }

    @PostMapping("/register")
    public Mono<User> register(@Valid User user){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(Role.ROLE_USER));

        return userService.save(user);
    }
}
