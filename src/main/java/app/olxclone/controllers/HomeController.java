package app.olxclone.controllers;

import app.olxclone.services.AdService;
import app.olxclone.services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final CategoryService categoryService;
    private final AdService adService;

    public HomeController(CategoryService categoryService, AdService adService){
        this.categoryService = categoryService;
        this.adService = adService;
    }

    @GetMapping({"", "/", "/home"})
    public String getHome(Model model){
        model.addAttribute("categories", categoryService.getCategories().collectList().block());
        model.addAttribute("ads", adService.getAds().collectList().block());

        return "index";
    }
}
