package com.harmoush.todo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends MongoRepository<Todo, String> {

    Todo findByTitle(String title);

    List<Todo> findAllByUserId(String userId);
}
