package app.olxclone.controllers;

import app.olxclone.Util.JWTUtil;
import app.olxclone.domain.Ad;
import app.olxclone.domain.User;
import app.olxclone.repositories.UserRepository;
import app.olxclone.services.AdService;
import app.olxclone.services.CategoryService;
import app.olxclone.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@Controller
@ResponseBody
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AdController {
    private final CategoryService categoryService;
    private final AdService adService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    public AdController(CategoryService categoryService, AdService adService, UserService userService, UserRepository userRepository, JWTUtil jwtUtil){
        this.categoryService = categoryService;
        this.adService = adService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/ad/new")
    public Mono<Ad> postAd(@Valid Ad ad, @CookieValue("jwt") String token) {
        if(ad.getId().length() != 36){
            ad.setId(UUID.randomUUID().toString());

            Mono<User> user = userService.findByUsername(jwtUtil.getUsernameFromToken(token));

            user.flatMap(user1 -> {
                user1.getAds().add(ad.getId());
                return userService.save(user1);
            });

            Mono<Ad> savedAd = adService.save(ad);

            return savedAd;
        }else {
            return adService.update(ad);
        }
    }

    @GetMapping("/ads/delete")
    Mono<?> deleteAd(@RequestParam String adId, @CookieValue("jwt") String token){
        Mono<User> user = userService.findByUsername(jwtUtil.getUsernameFromToken(token));

        user.map(user1 -> {
            user1.getAds().remove(adId);
            return userService.save(user1);
        });

        return adService.deleteById(adId);
    }

    @GetMapping("/ads")
    Flux<Ad> getAds(){
        return adService.getAds();
    }

    @GetMapping("/ad/{adId}")
    Mono<Ad> getAd(@PathVariable String adId){
        return adService.findById(adId);
    }

    @GetMapping("/adFavorite/{adId}")
    Mono<?> adFovorite(@PathVariable String adId, @CookieValue(name = "jwt") String token){
        Mono<User> user = userService.findByUsername(jwtUtil.getUsernameFromToken(token));

        return user.flatMap(user1 -> {
            user1.getFavorites().add(adId);
            return userService.update(user1);
        });
    }

    @GetMapping("/removeFavorite/{adId}")
    Mono<?> removeFavorite(@PathVariable String adId, @CookieValue(name = "jwt") String token){
        Mono<User> user = userService.findByUsername(jwtUtil.getUsernameFromToken(token));

        return user.flatMap(user1 -> {
            user1.getFavorites().remove(adId);
            return userService.update(user1);
        });
    }

    @GetMapping("/checkFavorite/{adId}")
    Mono<Boolean> checkFavorite(@PathVariable String adId, @CookieValue(name = "jwt") String token){
        Mono<User> user = userService.findByUsername(jwtUtil.getUsernameFromToken(token));

        user = user.filter(user1 -> user1.getFavorites().contains(adId));

        return user.hasElement();
    }

    @GetMapping("/ads/filter")
    Flux<Ad> getAdsByFilter(@RequestParam(required = false) String favorite, @RequestParam(required = false) String category,
                            @RequestParam(required = false) String username, @RequestParam(required = false) String searchText,
                            @RequestParam(required = false) String negotiable, @RequestParam(required = false) String state,
                            @RequestParam(required = false) String minPrice, @RequestParam(required = false) String maxPrice,
                            @CookieValue(name = "jwt") String token){
        Flux<Ad> ads = adService.getAds();
        if(favorite != null) {
            if(favorite.equals("true")) {
                ads = ads.filterWhen(x -> {
                    final Mono<User>[] user = new Mono[]{userService.findByUsername(jwtUtil.getUsernameFromToken(token))};
                    user[0] = user[0].filter(user1 -> user1.getFavorites().contains(x.getId()));
                    return user[0].hasElement();
                });
            }else{
                ads = ads.filterWhen(x -> {
                    final Mono<User>[] user = new Mono[]{userService.findByUsername(jwtUtil.getUsernameFromToken(token))};
                    user[0] = user[0].filter(user1 -> !user1.getFavorites().contains(x.getId()));
                    return user[0].hasElement();
                });
            }
        }
        if(category != null){
            ads = ads.filter(x -> x.getCategoryName().equals(category));
        }
        if(username != null){
            ads = ads.filter(x -> x.getUsername().equals(username));
        }
        if(searchText != null){
            ads = ads.filter(x -> x.getTitle().contains(searchText));
        }
        if(negotiable != null){
            if(negotiable.equals("true")){
                ads = ads.filter(x -> x.getNegotiable().equals(true));
            }else{
                ads = ads.filter(x -> x.getNegotiable().equals(false));
            }
        }
        if(state != null){
            if(state.equals("Used")){
                ads = ads.filter(x -> x.getState().equals("Used"));
            }else{
                ads = ads.filter(x -> x.getState().equals("New"));
            }
        }
        if(minPrice != null){
            ads = ads.filter(x -> Integer.parseInt(x.getPrice()) > Integer.parseInt(minPrice));
        }
        if(maxPrice != null){
            ads = ads.filter(x -> Integer.parseInt(x.getPrice()) < Integer.parseInt(maxPrice));
        }
        return ads;
    }
}