package com.harmoush.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.harmoush.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TodoControllerTest extends BaseControllerTest {

    @MockBean
    protected TodoService todoService;

    @Test
    public void whenFindAll_returnTodoList() throws Exception {
        Todo todo1 = new Todo("1", "Todo 1", "Description of Todo 1");
        Todo todo2 = new Todo("2", "Todo 2", "Description of Todo 2");
        List<Todo> result = Arrays.asList(todo1, todo2);

        BDDMockito.given(todoService.findAllTodosByUser(anyString())).willReturn(result);

        mockMvc.perform(doGet("/api/v1/todos").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", equalTo(todo1.getTitle())));
    }

    @Test
    public void whenFindTodoById_returnTodo() throws Exception {
        Todo todo = new Todo("1", "Todo 1", "Description of Todo 1");

        BDDMockito.given(todoService.getTodoById(anyString())).willReturn(todo);

        mockMvc.perform(doGet("/api/v1/todos/1").contentType(MediaType.APPLICATION_JSON))
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
                        doPost("/api/v1/todos").contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(todo))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", equalTo(todo.getTitle())))
                .andExpect(jsonPath("$.description", equalTo(todo.getDescription())));
    }
}