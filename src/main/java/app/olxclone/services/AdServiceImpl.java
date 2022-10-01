package app.olxclone.services;

import app.olxclone.commands.AdCommand;
import app.olxclone.converters.AdCommandToAd;
import app.olxclone.converters.AdToAdCommand;
import app.olxclone.domain.Ad;
import app.olxclone.repositories.AdRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AdServiceImpl implements AdService{
    private final AdRepository adRepository;
    private final AdCommandToAd adCommandToAd;
    private final AdToAdCommand adToAdCommand;

    public AdServiceImpl(AdRepository adRepository, AdCommandToAd adCommandToAd, AdToAdCommand adToAdCommand){
        this.adRepository = adRepository;
        this.adCommandToAd = adCommandToAd;
        this.adToAdCommand = adToAdCommand;
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
    public Mono<AdCommand> saveAdCommand(AdCommand adCommand) {
        return adRepository.save(adCommandToAd.convert(adCommand)).map(adToAdCommand::convert);
    }

    @Override
    public Mono<Ad> findByTitle(String title) {
        return adRepository.findByTitle(title);
    }
}
