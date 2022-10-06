package app.olxclone.controllers;

import app.olxclone.domain.Ad;
import app.olxclone.domain.User;
import app.olxclone.repositories.UserRepository;
import app.olxclone.services.AdService;
import app.olxclone.services.CategoryService;
import app.olxclone.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AdController {
    private final CategoryService categoryService;
    private final AdService adService;
    private final UserService userService;
    private final UserRepository userRepository;

    public AdController(CategoryService categoryService, AdService adService, UserService userService, UserRepository userRepository){
        this.categoryService = categoryService;
        this.adService = adService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @ResponseBody
    @PostMapping("/ad/new")
    public Map<String, Object> postAd(@Valid Ad ad, BindingResult result) {
        Map<String, ArrayList<String>> cause = new HashMap<>();
        Map<String, Object> response = new HashMap<>();
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
        }else{
            Ad savedAd = adService.save(ad).block();
            User user = userService.findByUsername(savedAd.getUsername()).block();
            user.getAds().add(savedAd.getId());
            User savedUser = userService.save(user).block();

            response.put("succes", savedAd);
        }
        return response;
    }

    @ResponseBody
    @GetMapping("/ads")
    Flux<Ad> getAds(){
        return adService.getAds();
    }

    @ResponseBody
    @GetMapping("/ad/{adId}")
    Mono<Ad> getAd(@PathVariable String adId){
        return adService.findById(adId);
    }
}
