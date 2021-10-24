package com.harmoush.todo;

import com.harmoush.error.ConflictException;
import com.harmoush.error.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(value = SpringExtension.class)
class TodoServiceTest {

    @MockBean
    private TodoRepository todoRepository;

    @Autowired
    private TodoService todoService;

    @Test
    void whenFindAll_returnTodoList() {
        Todo todo1 = new Todo("1", "Todo 1", "Description of Todo 1");
        Todo todo2 = new Todo("2", "Todo 2", "Description of Todo 2");
        List<Todo> result = Arrays.asList(todo1, todo2);

        BDDMockito.given(todoRepository.findAll()).willReturn(result);

        Assertions.assertThat(todoService.findAllTodos())
                .hasSize(2)
                .contains(todo1, todo2);
    }

    @Test
    void whenGetTodoById_returnTodo() {
        Todo todo = new Todo("1", "Todo 1", "Description of Todo 1");

        BDDMockito.given(todoRepository.findById(BDDMockito.anyString()))
                .willReturn(Optional.of(todo));

        Assertions.assertThat(todoService.getTodoById("1")).isEqualTo(todo);
    }

    @Test
    void whenInvalidId_getTodoById_shouldThrowNotFoundException() {
        BDDMockito.given(todoRepository.findById(BDDMockito.anyString())).willReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> {
            todoService.getTodoById("1");
        });
    }

    @Test
    void whenAddNewTodo_createNewTodo() {
        Todo todo = new Todo("1", "Todo 1", "Description of Todo 1");

        BDDMockito.given(todoRepository.insert(todo)).willReturn(todo);

        Assertions.assertThat(todoService.addNewTodo(todo)).isEqualTo(todo);
    }

    @Test
    void whenAddExistingTodo_shouldThrowConflictException() {
        Todo todo = new Todo("1", "Todo 1", "Description of Todo 1");

        BDDMockito.given(todoRepository.findByTitle(BDDMockito.anyString()))
                .willReturn(todo);

        assertThrows(ConflictException.class, () -> {
            todoService.addNewTodo(todo);
        });
    }

    // when dependency injection using field autowire.
    @TestConfiguration
    static class TodoServiceContextConfiguration {
        @Bean
        public TodoService todoService() {
            return new TodoService();
        }
    }
}