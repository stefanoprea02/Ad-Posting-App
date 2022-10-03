package app.olxclone.repositories;

import app.olxclone.domain.Ad;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AdRepository extends ReactiveMongoRepository<Ad, String> {
    Mono<Ad> findByTitle(String title);

    @Query("{'id' :  ?0}")
    Mono<Ad> findById(String id);
}
