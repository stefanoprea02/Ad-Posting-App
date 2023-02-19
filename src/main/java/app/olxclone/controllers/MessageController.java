package app.olxclone.controllers;

import app.olxclone.Util.JWTUtil;
import app.olxclone.domain.Message;
import app.olxclone.domain.User;
import app.olxclone.services.MessageService;
import app.olxclone.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.*;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@ResponseBody
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;
    private final Sinks.Many<Message> chatSink;

    public MessageController(MessageService messageService, UserService userService, JWTUtil jwtUtil) {
        this.messageService = messageService;
        this.userService = userService;
        this.chatSink = Sinks.many().multicast().directBestEffort();
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE,value="/messages")
    public Flux<Message> getMessages(@RequestParam("receiver") String receiver){
        Flux<Message> messages = messageService.findBySenderOrReceiver(receiver);

        return Flux.concat(messages, chatSink.asFlux().filter(msg -> msg.getReceiver().equals(receiver) || msg.getSender().equals(receiver))
                .map(msg -> {
                    if(msg.getReceiver().equals(receiver)){
                        msg.setIsSent(true);
                        messageService.save(msg);
                    }
                    return msg;
                }));
    }

    @GetMapping("/messages/all")
    public Flux<Message> getAllMessages(@RequestParam("receiver") String receiver){
        return messageService.findBySenderOrReceiver(receiver);
    }

    @PostMapping("/messages")
    public Mono<Message> postMessage(@Valid @RequestBody Message message){
        if(message.getId().length() != 36)
            message.setId(UUID.randomUUID().toString());

        chatSink.emitNext(message, ((signalType, emitResult) -> emitResult == Sinks.EmitResult.FAIL_NON_SERIALIZED));

        return updateUser(message).then(messageService.save(message));
    }

    private Flux<User> updateUser(Message message){
        return Flux.concat(userService.findByUsername(message.getSender()), userService.findByUsername(message.getReceiver()))
                .flatMap(u -> {
                    if(u.getUsername().equals(message.getSender()))
                        u.getConversations().add(message.getReceiver());
                    else
                        u.getConversations().add(message.getSender());
                    return userService.update(u);
                });
    }
}
