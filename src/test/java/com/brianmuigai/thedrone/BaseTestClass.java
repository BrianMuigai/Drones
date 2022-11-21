package com.brianmuigai.thedrone;

import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Type;
import java.time.OffsetDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class BaseTestClass {
    @Autowired
    protected MockMvc mvc;

    protected String mapToJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz) {
        Gson g = new GsonBuilder().registerTypeAdapter(OffsetDateTime.class, (JsonDeserializer<OffsetDateTime>)
                (json1, typeOfT, context) -> OffsetDateTime.parse(json1.getAsString())).create();
        return g.fromJson(json, clazz);
    }
}
