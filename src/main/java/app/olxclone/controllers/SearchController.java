package app.olxclone.controllers;

import app.olxclone.domain.Ad;
import app.olxclone.domain.Category;
import app.olxclone.domain.User;
import app.olxclone.services.AdService;
import app.olxclone.services.CategoryService;
import app.olxclone.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @PostMapping("/search/username")
    public Flux<User> searchUser(String searchText){
        return userService.findByUsernameLike(searchText);
    }

    @PostMapping("/search/ad")
    public Flux<Ad> searchAd(String searchText){
        return adService.findByTitleLike(searchText);
    }

    @PostMapping("/search/category")
    public Flux<Category> searchCategory(String searchText){
        return categoryService.findByDescriptionLike(searchText);
    }
}
