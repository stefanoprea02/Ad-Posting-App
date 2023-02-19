package app.olxclone.services;

import app.olxclone.domain.Message;
import app.olxclone.repositories.MessageRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MessageServiceImpl implements MessageService {
    private MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    @Override
    public Flux<Message> findBySender(String sender) {
        return messageRepository.findBySender(sender);
    }

    @Override
    public Flux<Message> findByReceiver(String receiver) {
        return messageRepository.findByReceiver(receiver);
    }

    @Override
    public Flux<Message> findBySenderOrReceiver(String name) {
        return Flux.merge(messageRepository.findByReceiver(name), messageRepository.findBySender(name));
    }

    @Override
    public Mono<Message> save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Mono<Message> update(Message message) {
        return messageRepository.findById(message.getId())
                .map(u -> message)
                .flatMap(messageRepository::save);
    }
}
