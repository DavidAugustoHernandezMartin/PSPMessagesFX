package augusto.hernandez.messagesfx.models;

public class User {
    private String id,name,password,image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
/*const User = mongoose.model("users",new mongoose.Schema({
    name: {
      type:String,
      required: true,
      minLength: 1,
      trim:true,
      match: /^[a-zA-Z0-9]+$/
    },
    password: {
      type: String,
      required: true,
      minLength: 4
    },
    image: {
      type:String,
      required:true,
      trim:true
    }
  }))*/