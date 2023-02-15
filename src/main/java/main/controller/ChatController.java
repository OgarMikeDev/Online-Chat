package main.controller;

import main.model.DTO.DTOMessage;
import main.model.DTO.MessageMapper;
import main.model.Message;
import main.repository.MessageRepository;
import main.model.User;
import main.repository.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ChatController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;


    @GetMapping("/init")
    //TODO: check sessionId. If found => true, if no => false
    public HashMap<String, Boolean> getUser() {
        HashMap<String, Boolean> mapForResponse = new HashMap<>();

        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();

        Optional<User> foundUser = userRepository.findUserBySessionId(sessionId);

        mapForResponse.put("result", foundUser.isPresent());

        return mapForResponse;
    }


    @PostMapping("/auth")
    public User createUser(@RequestParam String name) {
        //TODO:
        // - create User with name, sessionId
        // - save User to DB
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();

        User user = new User();
        user.setName(name);
        user.setSessionId(sessionId);

        return userRepository.save(user);
    }


    @GetMapping("/user")
    public List<User> getUsersList() {
        return userRepository.findAll();
    }


    @PostMapping("/message")
    public Map<String, Boolean> sendMessage(@RequestParam String message) {
        if (message.isEmpty()) {
            return Map.of("result", false);
        }

        HashMap<String, Boolean> response = new HashMap<>();
        String sessionIdTwo = RequestContextHolder.currentRequestAttributes().getSessionId();
        User user = userRepository.findUserBySessionId(sessionIdTwo).get();

        Message msg = new Message();
        msg.setUser(user);
        msg.setDatetime(LocalDateTime.now());
        msg.setMessage(message);
        messageRepository.saveAndFlush(msg);
        return Map.of("result", true);
    }


    @GetMapping("/message")
    public List<DTOMessage> getMessagesList() {
        return messageRepository
                .findAll(Sort.by(Sort.Direction.ASC, "datetime"))
                .stream()
                .map(message -> MessageMapper.mapMessageDTO(message))
                .collect(Collectors.toList());
    }
}
