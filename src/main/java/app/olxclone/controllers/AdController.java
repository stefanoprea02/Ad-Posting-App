package app.olxclone.controllers;

import app.olxclone.commands.AdCommand;
import app.olxclone.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
public class AdController {
    private final CategoryService categoryService;

    public AdController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("/ad/new")
    public String newAd(Model model){
        model.addAttribute("ad", new AdCommand());
        model.addAttribute("categories", categoryService.getCategories().collectList().block());

        return "adform";
    }

    @PostMapping("/ad/")
    public String postAd(@Valid @ModelAttribute("ad") AdCommand adCommand, BindingResult result, Model model){
        if(result.hasErrors()){
            result.getAllErrors().forEach(objectError -> {log.debug(objectError.toString());});
            model.addAttribute("categories", categoryService.getCategories().collectList().block());
            return "adform";
        }
        return "index";
    }
}
