package app.olxclone.services;

import app.olxclone.domain.User;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> findByUsername(String username);

    Mono<User> findById(String id);

    Mono<User> update(User user);

    Mono<User> save(User user);
}
