package augusto.hernandez.messagesfx.models.responses;

import java.util.Optional;

public abstract class OkResponse<T> {
    public boolean ok;
    public T error;
}

