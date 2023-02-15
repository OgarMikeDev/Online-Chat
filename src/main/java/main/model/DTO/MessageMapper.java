package main.model.DTO;

import main.model.Message;

public class MessageMapper {
    public static DTOMessage mapMessageDTO (Message message) {
        DTOMessage dtoMessage = new DTOMessage();
        dtoMessage.setText(message.getMessage());
        dtoMessage.setUsername(message.getUser().getName());
        dtoMessage.setDatetime(message.getDatetime().toString());
        return dtoMessage;
    }
}
