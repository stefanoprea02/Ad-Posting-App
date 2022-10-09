package app.olxclone.services;

import app.olxclone.domain.Ad;
import app.olxclone.repositories.AdRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AdServiceImpl implements AdService{
    private final AdRepository adRepository;
    public AdServiceImpl(AdRepository adRepository){
        this.adRepository = adRepository;
    }

    @Override
    public Flux<Ad> getAds() {
        return adRepository.findAll();
    }

    @Override
    public Mono<Ad> findById(String id) {
        return adRepository.findById(id);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return adRepository.deleteById(id);
    }

    @Override
    public Mono<Ad> save(Ad ad) {
        return adRepository.save(ad);
    }

    @Override
    public Mono<Ad> findByTitle(String title) {
        return adRepository.findByTitle(title);
    }

    @Override
    public Flux<Ad> findByTitleLike(String text) {
        return adRepository.findByTitleLike(text);
    }
}
