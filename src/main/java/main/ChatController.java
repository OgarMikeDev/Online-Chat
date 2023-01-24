package main;

import main.model.Message;
import main.model.MessageRepository;
import main.model.User;
import main.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();

        Optional<User> userOpt = userRepository.findBySessionId(sessionId);


        response.put("result", userOpt.isPresent());
        return response;
    }


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


    @GetMapping("/message")
    public List<String> getMessagesList() {
        return null;
    }


    @PostMapping("/message")
    public Map<String, Boolean> sendMessage(@RequestParam String message) {
        if (message.isEmpty()) {
            return Map.of("result", false);
        }

        HashMap<String, Boolean> response = new HashMap<>();


        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();

        User user = userRepository.findBySessionId(sessionId).get();
        Message msg = new Message();
        msg.setMessage(message);
        msg.setDateTime(LocalDateTime.now());
        msg.setId(user.getId());
        msg.setUser(user);

        messageRepository.save(msg);
        response.put("result", true);

        return response;
    }


    @GetMapping("/user")
    public HashMap<Integer, String> getUsersList() {
        return null;
    }
}
