package ru.javawebinar.topjava.web;

import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.model.User;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.UserTestData.user;

public class ResourceControllerTest extends AbstractControllerTest{
    @Test
    void resources() throws Exception {
        perform(MockMvcRequestBuilders.get("/resources/css/style.css"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.valueOf("text/css")));
    }
}
