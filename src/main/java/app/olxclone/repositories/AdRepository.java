package app.olxclone.repositories;

import app.olxclone.domain.Ad;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AdRepository extends ReactiveMongoRepository<Ad, String> {
}
