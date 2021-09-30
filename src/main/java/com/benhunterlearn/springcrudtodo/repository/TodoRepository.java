package com.benhunterlearn.springcrudtodo.repository;

import com.benhunterlearn.springcrudtodo.model.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<Todo, Long> {
}
