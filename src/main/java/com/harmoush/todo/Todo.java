package com.harmoush.todo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Document(collection = "Todos")
public class Todo {

    @Id
    private String id;

    @NotNull(message = "Title is required.")
    @NotBlank(message = "Title shouldn't be empty or blank.")
    @Size(min = 3, message = "Title must be at least 3 characters.")
    private String title;

    @NotNull(message = "Description is required.")
    @NotBlank(message = "Description shouldn't be empty or blank.")
    private String description;

    @JsonIgnore
    private LocalDateTime timestamp;

    public Todo() {
        this.timestamp = LocalDateTime.now();
    }

    public Todo(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
