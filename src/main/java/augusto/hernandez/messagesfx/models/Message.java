package augusto.hernandez.messagesfx.models;

import java.time.LocalDate;

public class Message {
    private String id,from,to,message;
    private LocalDate sent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getSent() {
        return sent;
    }

    public void setSent(LocalDate sent) {
        this.sent = sent;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;
}
/*const Message = mongoose.model("messages",new mongoose.Schema({
    from: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'users',
        required: true
    },
    to: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'users',
        required: true
    },
    message: {
        type:String,
        required:true,
        trim:true,
        minlength:1
    },
    image:{
        type:String
    },
    sent:{
        type:String,
        required:true,
        trim:true,
        maxlength:10
    }
  }))*/