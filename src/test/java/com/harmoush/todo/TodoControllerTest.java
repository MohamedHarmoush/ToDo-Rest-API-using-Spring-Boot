package com.harmoush.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Test
    public void whenFindAll_returnTodoList() throws Exception {
        Todo todo1 = new Todo("1", "Todo 1", "Description of Todo 1");
        Todo todo2 = new Todo("2", "Todo 2", "Description of Todo 2");
        List<Todo> result = Arrays.asList(todo1, todo2);

        BDDMockito.given(todoService.findAllTodos()).willReturn(result);

        mockMvc.perform(get("/api/v1/todos").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", equalTo(todo1.getTitle())));
    }

    @Test
    public void whenFindTodoById_returnTodo() throws Exception {
        Todo todo = new Todo("1", "Todo 1", "Description of Todo 1");

        BDDMockito.given(todoService.getTodoById(BDDMockito.anyString())).willReturn(todo);

        mockMvc.perform(get("/api/v1/todos/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("@.title", equalTo(todo.getTitle())))
                .andExpect(jsonPath("@.description", equalTo(todo.getDescription())));
    }

    @Test
    public void whenPostTodo_thenCreateTodo() throws Exception {
        Todo todo = new Todo("1", "Todo 1", "Description of Todo 1");

        BDDMockito.given(todoService.addNewTodo(Mockito.any(Todo.class))).willReturn(todo);

        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        mockMvc.perform(
                        post("/api/v1/todos").contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(todo))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", equalTo(todo.getTitle())))
                .andExpect(jsonPath("$.description", equalTo(todo.getDescription())));
    }
}