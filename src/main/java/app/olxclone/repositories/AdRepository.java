package app.olxclone.repositories;

import app.olxclone.domain.Ad;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AdRepository extends ReactiveMongoRepository<Ad, String> {
}
