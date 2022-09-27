package app.olxclone.controllers;

import app.olxclone.domain.Category;
import app.olxclone.services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class HomeController {

    private final CategoryService categoryService;

    public HomeController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping({"", "/", "/home"})
    public String getHome(Model model){
        model.addAttribute("categories", categoryService.getCategories().collectList().block());

        return "index";
    }
}
