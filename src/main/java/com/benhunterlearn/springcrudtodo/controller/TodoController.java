package com.benhunterlearn.springcrudtodo.controller;

import com.benhunterlearn.springcrudtodo.model.Todo;
import com.benhunterlearn.springcrudtodo.model.TodoDto;
import com.benhunterlearn.springcrudtodo.repository.TodoRepository;
import com.benhunterlearn.springcrudtodo.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/todo")
public class TodoController {
    TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @PostMapping("")
    public TodoDto postCreateTodo(@RequestBody TodoDto todoDto) {
        return this.service.createTodo(todoDto);
    }

    @GetMapping("")
    public Iterable<TodoDto> getTodoList() {
        return this.service.getTodoList();
    }

    @GetMapping("{id}")
    public TodoDto getTodoById(@PathVariable Long id) {
        return this.service.getTodoById(id);
    }

    @PatchMapping("{id}")
    public TodoDto patchUpdatesTodoById(@PathVariable Long id, @RequestBody TodoDto todoDto) {
        return this.service.patchById(id, todoDto);
    }

    @DeleteMapping("{id}")
    public String deleteTodoById(@PathVariable Long id) {
        return this.service.deleteById(id);
    }
}
