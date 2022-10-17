package app.olxclone.repositories;

import app.olxclone.domain.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByUsername(String username);

    Mono<Boolean> existsByUsername(String username);

    Mono<Boolean> existsByEmail(String email);

    @Query("{'id' :  ?0}")
    Mono<User> findById(String id);

    Flux<User> findByUsernameLike(String text);

    Mono<User> save(User user);
}
