package com.harmoush.todo;

import com.harmoush.error.ConflictException;
import com.harmoush.error.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    /**
     * Get all Todo for specific user
     *
     * @return list of todos
     */

    public List<Todo> findAllTodosByUser(String userId) {
        return todoRepository.findAllByUserId(userId);
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
    public Todo getTodoById(String id) throws NotFoundException {
        try {
            return todoRepository.findById(id).get();
        } catch (NoSuchElementException ex) {
            String message = String.format(Locale.ROOT, "This record with id %s not found in our db.", id);
            throw new NotFoundException(message);
        }
    }

    /**
     * Create a new Todo
     *
     * @param todo
     * @return insertedTodo
     */
    public Todo addNewTodo(Todo todo) {
        if (todoRepository.findByTitle(todo.getTitle()) != null) {
            String message = String.format(Locale.ROOT, "This record with title {%s} already exist in our db.", todo.getTitle());
            throw new ConflictException(message);
        }
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
