package app.olxclone.bootstrap;

import app.olxclone.domain.Category;
import app.olxclone.repositories.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class AppBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    CategoryRepository categoryRepository;

    public AppBootstrap(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event){
        if(categoryRepository.count().block() == 0)
            loadCategories();
        else{
            System.out.println("NU");
        }

        try{
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException ie){
            Thread.currentThread().interrupt();
        }

        log.error("#######");
        log.error("Count: " + categoryRepository.count().block().toString());

    }

    private void loadCategories(){

        Set<String> categorii = new HashSet<>(Arrays.asList("Auto, moto si ambarcatiuni", "Imobiliare", "Locuri de munca",
                "Electronice si electrocasnice", "Moda si frumusete", "Piese auto", "Casa si gradina",
                "Mama si copilul", "Sport", "Animale de companie",
                "Agro si industrie", "Servicii, afaceri, echipamente firme"));

        for(String nume : categorii){
            Category category = new Category();
            category.setDescription(nume);
            categoryRepository.save(category).block();
        }
    }
}
