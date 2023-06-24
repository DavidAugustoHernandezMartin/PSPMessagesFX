package augusto.hernandez.messagesfx.models.responses;

import augusto.hernandez.messagesfx.models.Message;

import java.util.List;

public class MessagesResponse extends OkResponse<ErrorResponse>{
    private List<Message> messages;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
