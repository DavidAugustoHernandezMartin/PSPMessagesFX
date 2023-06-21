package augusto.hernandez.messagesfx.utils;

import com.google.gson.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public class GsonService {
    public final static Gson gson;

    static {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class,
                        (JsonSerializer<LocalDate>)(date, typeOfSrc, context) ->
                                new JsonPrimitive(date.toString()))
                .registerTypeAdapter(LocalDate.class,
                        (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) ->
                                ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDate())
                .create();
    }
}
