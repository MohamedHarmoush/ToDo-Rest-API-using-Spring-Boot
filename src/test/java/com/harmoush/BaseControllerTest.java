package com.harmoush;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.harmoush.security.AppUser;
import com.harmoush.security.LoginRequest;
import com.harmoush.security.UserService;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class BaseControllerTest {

    public static final String USERNAME_FOR_TEST = "Harmoush";
    public static final String EMAIL_FOR_TEST = "mkotb.harmoush@gmail.com";
    public static final String PASSWORD_FOR_TEST = "123456x";
    public static final String AUTH_HEADER = "Authorization";

    @Autowired
    protected MockMvc mockMvc;
    @MockBean
    protected UserService userService;

    @BeforeEach
    public void setup() {
        AppUser user = new AppUser(USERNAME_FOR_TEST, EMAIL_FOR_TEST, new BCryptPasswordEncoder().encode(PASSWORD_FOR_TEST));
        user.setId("123");

        BDDMockito.given(userService.loadUserByUsername(anyString())).willReturn(user);
    }

    protected MockHttpServletRequestBuilder doPost(String url) {
        return post(url).header(AUTH_HEADER, getHeader());
    }

    protected MockHttpServletRequestBuilder doGet(String url) {
        return get(url).header(AUTH_HEADER, getHeader());
    }

    private String getHeader() {
        try {
            MvcResult result = login(USERNAME_FOR_TEST, PASSWORD_FOR_TEST).andReturn();
            String token = JsonPath.read(result.getResponse().getContentAsString(), "$.token");
            return String.format("Bearer %s", token);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ResultActions login(String username, String password) throws Exception {
        LoginRequest loginRequest = new LoginRequest(username, password);
        return mockMvc.perform(post("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequest))
        );
    }
}
