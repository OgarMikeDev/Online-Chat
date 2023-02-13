package org.example.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.model.Message;
import org.example.model.User;
import org.example.model.dto.MessageResponseDTO;
import org.example.model.mapper.MessageMapper;
import org.example.repository.MessageRepository;
import org.example.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

  private final UserRepository userRepository;
  private final MessageRepository messageRepository;


  @GetMapping
  public List<MessageResponseDTO> getMessagesList() {
    return messageRepository
        .findAll(Sort.by(Sort.Direction.ASC, "date"))
        .stream()
        .map(MessageMapper::messageToMessageDto)
        .collect(Collectors.toList());
  }

  @PostMapping
  public Message sendMessage(@RequestParam String message) {
    String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
    User user = userRepository.findUserBySessionId(sessionId).get();

    Message msg = new Message();
    msg.setMessage(message);
    msg.setDate(LocalDateTime.now());
    msg.setUser(user);

    return messageRepository.save(msg);
  }

}
