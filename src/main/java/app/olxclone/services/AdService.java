package app.olxclone.services;

import app.olxclone.domain.Ad;
import app.olxclone.domain.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AdService {
    Flux<Ad> getAds();

    Mono<Ad> findById(String id);

    Mono<Void> deleteById(String id);
}
