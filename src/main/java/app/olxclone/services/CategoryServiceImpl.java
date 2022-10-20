package app.olxclone.services;

import app.olxclone.domain.Category;
import app.olxclone.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Flux<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Mono<Category> findById(String id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Mono<Category> findByDescription(String description) {
        return categoryRepository.findByDescription(description);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return categoryRepository.deleteById(id);
    }

    @Override
    public Flux<Category> findByDescriptionLike(String text) {
        return categoryRepository.findByDescriptionContainsIgnoreCase(text);
    }
}
