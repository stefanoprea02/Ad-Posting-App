package app.olxclone.bootstrap;

import app.olxclone.domain.Category;
import app.olxclone.domain.Role;
import app.olxclone.domain.User;
import app.olxclone.repositories.CategoryRepository;
import app.olxclone.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    }

    private void loadCategories() throws IOException {

        System.out.println("DA");

        Set<String> categorii = new HashSet<>(Arrays.asList("auto,-moto-and-boats", "houses", "jobs",
                "electronics", "fashion-and-beauty", "auto-parts", "house-and-garden",
                "mother-and-child", "sport", "pets",
                "agriculture", "services,-business,-company-equipment"));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setEmail("admin@gmail.com");
        user.setRoles(List.of(Role.ROLE_USER));
        userRepository.save(user).block();

        try {
            for (String nume : categorii) {
                Category category = new Category();
                category.setDescription(nume);

                String url = category.getDescription();
                var resource = new ClassPathResource("static/images/" + url + ".png");
                InputStream stream = resource.getInputStream();

                byte[] fileContent = stream.readAllBytes();
                category.setImage(fileContent);
                System.out.println(nume);
                categoryRepository.save(category).block();
            }
        } catch (IOException e){
            log.error(String.valueOf(e));

            e.printStackTrace();

            throw(e);
        }
    }
}
