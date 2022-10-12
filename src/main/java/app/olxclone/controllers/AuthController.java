package app.olxclone.controllers;

import app.olxclone.Util.JwtUtil;
import app.olxclone.domain.AuthCredentialsRequest;
import app.olxclone.domain.User;
import app.olxclone.services.UserService;
import ch.qos.logback.core.util.Duration;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "Authorization", allowCredentials = "true")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    private final UserService userService;

    @Value("${cookies.domain}")
    private String domain;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService){
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(AuthCredentialsRequest request){
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getUsername(),
                                    request.getPassword()
                            )
                    );
            User user = (User) authenticate.getPrincipal();

            String token = jwtUtil.generateToken(user);
            ResponseCookie cookie = ResponseCookie.from("jwt", token)
                    .domain(domain)
                    .path("/")
                    .maxAge(Duration.buildByDays(365).getMilliseconds())
                    .build();
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(token);
        }catch (BadCredentialsException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validate(@CookieValue(name = "jwt") String token, @AuthenticationPrincipal User user){
        try {
            Boolean isValid = jwtUtil.validateToken(token, user);
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
    public Mono<ResponseEntity<User>> checkUsername(@PathVariable String username){
        Mono<ResponseEntity<User>> responseEntityMono = userService.findByUsername(username)
                .map(x -> new ResponseEntity<>(x, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        return responseEntityMono;
    }

    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid User user, BindingResult result){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Map<String, ArrayList<String>> cause = new HashMap<>();
        Map<String, Object> response = new HashMap<>();

        User checkIfUserExists = userService.findByUsername(user.getUsername()).block();
        if(checkIfUserExists != null){
            ArrayList<String> list = new ArrayList<>();
            list.add("Username is already in use");
            cause.put("username", list);
        }

        if(result.hasErrors()){
            List<FieldError> errors = result.getFieldErrors();
            for(FieldError e : errors){
                if(cause.get(e.getField()) != null){
                    ArrayList<String> list = cause.get(e.getField());
                    list.add(e.getDefaultMessage());
                    cause.put(e.getField(), list);
                }else{
                    ArrayList<String> list = new ArrayList<>();
                    list.add(e.getDefaultMessage());
                    cause.put(e.getField(), list);
                }
            }
            response.put("error", cause);
            return ResponseEntity.ok().body(response);
        }else{
            if(checkIfUserExists != null){
                response.put("error", cause);
                return ResponseEntity.ok().body(response);
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userService.save(user).block();
            String token = jwtUtil.generateToken(savedUser);
            ResponseCookie cookie = ResponseCookie.from("jwt", token)
                    .domain(domain)
                    .path("/")
                    .maxAge(Duration.buildByDays(365).getMilliseconds())
                    .build();
            response.put("token", token);
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(response);
        }
    }
}
