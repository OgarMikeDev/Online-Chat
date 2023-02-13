package org.example.model.mapper;

import org.example.model.Message;
import org.example.model.dto.MessageResponseDTO;

public class MessageMapper {

  public static MessageResponseDTO messageToMessageDto(Message message) {
    MessageResponseDTO dtoMessage = new MessageResponseDTO();
    dtoMessage.setText(message.getMessage());
    dtoMessage.setUsername(message.getUser().getName());
    dtoMessage.setDatetime(message.getDate().toString());
    return dtoMessage;
  }

}
