package com.harmoush.todo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping(value = {"/", ""})
    public ResponseEntity<List<Todo>> getAllTodos() {
        List<Todo> result = todoService.findAllTodos();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable String id) {
        Todo result = todoService.getTodoById(id);
        return new ResponseEntity<>(result, result != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = {"/", ""})
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo todo) {
        Todo result = todoService.addNewTodo(todo);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable String id) {
        todoService.deleteTodoById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Todo> editTodoById(@PathVariable String id, @RequestBody Todo todo) {
        Todo result = todoService.editTodoById(id, todo);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
