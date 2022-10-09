package app.olxclone.repositories;

import app.olxclone.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
    Mono<Category> findByDescription(String description);
    Flux<Category> findByDescriptionLike(String text);
}
