package augusto.hernandez.messagesfx.models.responses;

import augusto.hernandez.messagesfx.models.User;

import java.util.List;

public class UsersResponse extends OkResponse<ErrorResponse>{
    private List<User> users;
}
