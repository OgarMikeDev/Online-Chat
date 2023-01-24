package main.dto;

import main.model.Message;

public class MessageMapper {

    public static DTOMessage map (Message message) {
        DTOMessage dtoMessage = new DTOMessage();

        dtoMessage.setText(message.getMessage());
        dtoMessage.setDatatime(message.getDateTime().toString());
        dtoMessage.setUsername(message.getUser().getName());

        return dtoMessage;
    }
}
