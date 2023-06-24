package augusto.hernandez.messagesfx.utils;

import com.google.gson.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GsonService {

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    public final static Gson gson;

    static {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class,
                        (JsonSerializer<LocalDate>)(date, typeOfSrc, context) ->
                                new JsonPrimitive(date.format(formatter)))
                .registerTypeAdapter(LocalDate.class,
                        (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) ->
                                LocalDate.parse(json.getAsJsonPrimitive().getAsString(), formatter))
                .create();
    }
}
