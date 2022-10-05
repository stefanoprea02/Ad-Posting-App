package app.olxclone.bootstrap;

import app.olxclone.domain.Category;
import app.olxclone.domain.User;
import app.olxclone.repositories.CategoryRepository;
import app.olxclone.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public AppBootstrap(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
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

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setEmail("admin@gmail.com");
        userRepository.save(user).block();

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
