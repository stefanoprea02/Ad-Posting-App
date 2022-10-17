package app.olxclone.controllers;

import app.olxclone.domain.Ad;
import app.olxclone.domain.Category;
import app.olxclone.domain.User;
import app.olxclone.services.AdService;
import app.olxclone.services.CategoryService;
import app.olxclone.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Controller
@ResponseBody
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class SearchController {
    private final UserService userService;
    private final CategoryService categoryService;
    private final AdService adService;

    public SearchController(UserService userService, CategoryService categoryService, AdService adService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.adService = adService;
    }

    @GetMapping("/search/username/{searchText}")
    public Flux<User> searchUser(@PathVariable String searchText){
        return userService.findByUsernameLike(searchText);
    }

    @GetMapping("/search/ad/{searchText}")
    public Flux<Ad> searchAd(@PathVariable String searchText){
        return adService.findByTitleLike(searchText);
    }

    @GetMapping("/search/category/{searchText}")
    public Flux<Category> searchCategory(@PathVariable String searchText){
        return categoryService.findByDescriptionLike(searchText);
    }
}
