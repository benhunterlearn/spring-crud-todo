package com.benhunterlearn.springcrudtodo.service;

import com.benhunterlearn.springcrudtodo.model.Todo;
import com.benhunterlearn.springcrudtodo.model.TodoDto;
import com.benhunterlearn.springcrudtodo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TodoService {
    TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public TodoDto createTodo(TodoDto todoDto) {
        TodoDto outputTodoDto = new TodoDto(this.repository.save(new Todo(todoDto)));
        return outputTodoDto;
    }

    public Iterable<TodoDto> getTodoList() {
        ArrayList<TodoDto> todoDtoIterable = new ArrayList<TodoDto>();
        for (Todo todo : this.repository.findAll()) {
            todoDtoIterable.add(new TodoDto(todo));
        }
        return todoDtoIterable;
    }

    public TodoDto patchById(Long id, TodoDto todoDto) {
        Todo currentTodo = this.repository.findById(id).get();
        currentTodo.patch(todoDto);
        currentTodo = this.repository.save(currentTodo);
        return new TodoDto(currentTodo);
    }

    public String deleteById(Long id) {
        this.repository.deleteById(id);
        return "SUCCESS";
    }

    public TodoDto getTodoById(Long id) {
        return new TodoDto(this.repository.findById(id).get());
    }
}
