package am.example.task.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
@Slf4j
public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T deserialize(String payload, TypeReference<T> type) throws IOException {
        return objectMapper.readValue(payload, type);
    }


    public static String serialize(Object obj) {
        String value = "";
        try {
            value = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("ERROR:", e);
        }
        return value;
    }
}