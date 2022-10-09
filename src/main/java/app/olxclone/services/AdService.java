package app.olxclone.services;

import app.olxclone.domain.Ad;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AdService {
    Flux<Ad> getAds();

    Mono<Ad> findById(String id);

    Mono<Void> deleteById(String id);

    Mono<Ad> findByTitle(String title);

    Mono<Ad> save(Ad ad);

    Flux<Ad> findByTitleLike(String text);
}
