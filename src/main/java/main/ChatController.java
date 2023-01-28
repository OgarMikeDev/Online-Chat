package main;
import main.DTO.DTOMessage;
import main.DTO.MessageMapper;
import main.model.Message;
import main.model.MessageRepository;
import main.model.User;
import main.model.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public HashMap<String, Boolean> init() {
        HashMap<String, Boolean> response = new HashMap<>();
        //if user exists return true
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        Optional<User> userOptional = userRepository.findUserBySessionId(sessionId);


        response.put("result", userOptional.isPresent());
        return response;
    }

    //Sample code "/auth"
    @PostMapping("/auth")
    public HashMap<String, Boolean> auth(@RequestParam String name) {
        HashMap<String, Boolean> response = new HashMap<>();
        //TODO:
        // - create User with name, sessionId
        // - save User to DB
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();

        User user = new User();
        user.setName(name);
        user.setSessionId(sessionId);

        userRepository.save(user);

        response.put("result", true);
        return response;
    }


    @PostMapping("/message")
    public Map<String, Boolean> sendMessage(@RequestParam String message) {
        if (Strings.isEmpty(message)) {
            return Map.of("result", false);
        }
        HashMap<String, Boolean> response = new HashMap<>();
        String sessionIdTwo = RequestContextHolder.currentRequestAttributes().getSessionId();
        User user = userRepository.findUserBySessionId(sessionIdTwo).get();

        Message msg = new Message();
        msg.setUser(user);
        msg.setDateTime(LocalDateTime.now());
        msg.setMessage(message);
        messageRepository.saveAndFlush(msg);
        return Map.of("result", true);
    }


    @GetMapping("/message")
    public List<DTOMessage> getMessagesList() {
        return messageRepository
                .findAll(Sort.by(Sort.Direction.ASC, "dateTime"))
                .stream()
                .map(message -> MessageMapper.map(message))
                .collect(Collectors.toList());
    }


    @GetMapping("/user")
    public HashMap<Integer, String> getUsersList() {
        return null;
    }
}
