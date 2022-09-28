package app.olxclone.controllers;

import app.olxclone.commands.AdCommand;
import app.olxclone.domain.Category;
import app.olxclone.services.AdService;
import app.olxclone.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Slf4j
@Controller
public class AdController {
    private final CategoryService categoryService;
    private final AdService adService;

    public AdController(CategoryService categoryService, AdService adService){
        this.categoryService = categoryService;
        this.adService = adService;
    }

    @GetMapping("/ad/new")
    public String newAd(Model model){
        model.addAttribute("ad", new AdCommand());
        model.addAttribute("categories", categoryService.getCategories().collectList().block());

        Category cat = categoryService.getCategories().blockFirst();
        log.error("&%^75");
        log.error(cat.getId());
        log.error(cat.getDescription());

        return "adform";
    }

    @PostMapping("/ad/")
    public String postAd(@Valid @ModelAttribute("ad") AdCommand adCommand, BindingResult result, @RequestParam("image1")
                         MultipartFile multipartFile, Model model){
        if(result.hasErrors()){
            result.getAllErrors().forEach(objectError -> {log.error(objectError.toString());});
            model.addAttribute("categories", categoryService.getCategories().collectList().block());
            return "adform";
        }
        AdCommand savedAd = adService.saveAdCommand(adCommand).block();

        //model.addAttribute("ad", savedAd);

        return "redirect:/";
    }
}
