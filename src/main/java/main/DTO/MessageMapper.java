package main.DTO;

import main.model.Message;

public class MessageMapper {
    public static DTOMessage map (Message message) {
        DTOMessage dtoMessage = new DTOMessage();
        dtoMessage.setText(message.getMessage());
        dtoMessage.setUsername(message.getUser().getName());
        dtoMessage.setDatetime(message.getDateTime().toString());
        return dtoMessage;
    }
}
