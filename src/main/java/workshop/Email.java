package workshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.io.IOException;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.io.IOException;

// Plain object, no kafka

public class Email   {
    // public getter/setter created by lombok
    public String id;
    public String from;
    public String to;
    public String subject;
    public String content;


    @JsonPOJOBuilder(withPrefix = "")
    public static class EmailBuilder {
        // Lombok will add constructor, setters, build method
    }

    private static ObjectMapper objectMapper = new ObjectMapper();

    public String toJSON() throws IOException {
        return  objectMapper.writeValueAsString(this);
    }

    public static  Email fromJson(String json) throws  IOException {
        return objectMapper.readValue(json, Email.class);
    }

}
