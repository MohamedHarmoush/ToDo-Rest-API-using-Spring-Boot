package com.harmoush.todo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /**
     * Get all Todo
     *
     * @return list of todos
     */
    public List<Todo> findAllTodos() {
        return todoRepository.findAll();
    }

    /**
     * Get TodoTask by Id
     *
     * @param id Todo
     * @return TodoTask
     */
    public Todo getTodoById(String id) {
        return todoRepository.findById(id).orElseGet(null);
    }

    /**
     * Create a new Todo
     *
     * @param todo
     * @return insertedTodo
     */
    public Todo addNewTodo(Todo todo) {
        return todoRepository.insert(todo);
    }

    /**
     * Delete Todo
     *
     * @param id todoId
     */
    public void deleteTodoById(String id) {
        todoRepository.deleteById(id);
    }

    /**
     * Edit a Todo
     *
     * @param todoId     id of editedTodo
     * @param editedTodo editedTodo
     * @return editedTodo
     */
    public Todo editTodoById(String todoId, Todo editedTodo) {
        editedTodo.setId(todoId);
        return todoRepository.save(editedTodo);
    }
}
