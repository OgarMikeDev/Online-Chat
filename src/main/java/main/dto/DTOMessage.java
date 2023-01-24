package main.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTOMessage {

    private String text;

    private String datatime;

    private String username;

    public DTOMessage() {

    }

    public DTOMessage(String text, String datatime, String username) {
        this.text = text;
        this.datatime = datatime;
        this.username = username;
    }
}
