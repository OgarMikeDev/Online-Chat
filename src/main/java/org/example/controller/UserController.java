package org.example.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserRepository userRepository;


  @GetMapping
  public List<User> getUsersList() {
    return userRepository.findAll();
  }

  @GetMapping("/{id}")
  public User getUser(@PathVariable("id") Integer id) {
    return userRepository.findById(id).orElse(null);
  }

  @PostMapping
  public User createUser(@RequestParam String name) {
    //TODO:
    // - create User with name, sessionId
    // - save User to DB
    String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();

    User user = new User();
    user.setName(name);
    user.setSessionId(sessionId);

    userRepository.save(user);

    return userRepository.save(user);
  }
}
