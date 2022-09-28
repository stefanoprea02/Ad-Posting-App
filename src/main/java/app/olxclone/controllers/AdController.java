package app.olxclone.controllers;

import app.olxclone.FileUploadUtil;
import app.olxclone.commands.AdCommand;
import app.olxclone.domain.Category;
import app.olxclone.services.AdService;
import app.olxclone.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

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

        return "adform";
    }

    @PostMapping("/ad/")
    public String postAd(@Valid @ModelAttribute("ad") AdCommand adCommand, BindingResult result, @RequestParam("imageFile")
                         MultipartFile[] multipartFile, Model model) throws IOException {
        if(result.hasErrors()){
            result.getAllErrors().forEach(objectError -> {log.error(objectError.toString());});
            model.addAttribute("categories", categoryService.getCategories().collectList().block());
            return "adform";
        }
        String[] fileNames = new String[8];
        int i = 0;
        for(MultipartFile file : multipartFile){
            if(file.getBytes().length > 0){
                fileNames[i++] = StringUtils.cleanPath(file.getOriginalFilename());
            }
        }
        adCommand.setImages(fileNames);

        AdCommand savedAd = adService.saveAdCommand(adCommand).block();

        String uploadDir = "ad-images/" + savedAd.getId();
        log.error(savedAd.getId());
        log.error(uploadDir);
        FileUploadUtil.saveFile(uploadDir, fileNames, multipartFile);

        //model.addAttribute("ad", savedAd);

        return "redirect:/";
    }
}
