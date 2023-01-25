package main;
import main.model.Message;
import main.model.User;
import main.model.UserRepository;
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


    @GetMapping("/init")
    //TODO: check sessionId. If found => true, if no => false
    public HashMap<String, Boolean> init() {
        HashMap<String, Boolean> response = new HashMap<>();
        //if user exists return true


        response.put("result", false);
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


    @GetMapping("/message")
    public List<String> getMessagesList() {
        return new ArrayList<>();
    }


    @PostMapping("/message")
    public Map<String, Boolean> sendMessage(@RequestParam String message) {
        HashMap<String, Boolean> response = new HashMap<>();
        response.put("message", false);

        return response;
    }


    @GetMapping("/user")
    public HashMap<Integer, String> getUsersList() {
        return null;
    }
}
