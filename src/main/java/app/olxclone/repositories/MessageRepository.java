package app.olxclone.repositories;

import app.olxclone.domain.Message;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MessageRepository extends ReactiveMongoRepository<Message, String> {
    Flux<Message> findBySender(String sender);
    Flux<Message> findByReceiver(String receiver);
}
