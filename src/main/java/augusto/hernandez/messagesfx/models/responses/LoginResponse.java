package augusto.hernandez.messagesfx.models.responses;

public class LoginResponse extends OkResponse<ErrorResponse>{
    private String id,token,name,image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
/*  {
        ok:true,
        token:"cadena",
        name:"cadena",
        image:"cadena"
    } */