package app.olxclone.bootstrap;

import app.olxclone.domain.Category;
import app.olxclone.repositories.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
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
            try {
                loadCategories();
            }catch (IOException e){
                log.error(e.toString());
            }
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

    private void loadCategories() throws IOException {

        Set<String> categorii = new HashSet<>(Arrays.asList("Auto, moto si ambarcatiuni", "Imobiliare", "Locuri de munca",
                "Electronice si electrocasnice", "Moda si frumusete", "Piese auto", "Casa si gradina",
                "Mama si copilul", "Sport", "Animale de companie",
                "Agro si industrie", "Servicii, afaceri, echipamente firme"));

        try {
            for (String nume : categorii) {
                Category category = new Category();
                category.setDescription(nume);

                String url = category.getDescriptionToUrl();
                var resource = new ClassPathResource("static/images/" + url + ".png");
                InputStream stream = resource.getInputStream();

                byte[] fileContent = stream.readAllBytes();
                category.setImage(fileContent);

                categoryRepository.save(category).block();
            }
        } catch (IOException e){
            log.error(String.valueOf(e));

            e.printStackTrace();

            throw(e);
        }
    }
}
