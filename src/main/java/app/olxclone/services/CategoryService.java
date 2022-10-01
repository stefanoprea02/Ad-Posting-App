package app.olxclone.services;

import app.olxclone.domain.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {
    Flux<Category> getCategories();

    Mono<Category> findById(String id);

    Mono<Category> findByDescription(String description);

    Mono<Void> deleteById(String id);
}
