package com.benhunterlearn.springcrudtodo.controller;

import com.benhunterlearn.springcrudtodo.model.Todo;
import com.benhunterlearn.springcrudtodo.model.TodoDto;
import com.benhunterlearn.springcrudtodo.repository.TodoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/todo")
public class TodoController {
    TodoRepository repository;

    public TodoController(TodoRepository repository) {
        this.repository = repository;
    }

    @PostMapping("")
    public TodoDto postCreateTodo(@RequestBody TodoDto todoDto) {
        TodoDto outputTodoDto = new TodoDto(this.repository.save(new Todo(todoDto)));
        return outputTodoDto;


    }

    @GetMapping("")
    public Iterable<TodoDto> getTodoList() {
        ArrayList<TodoDto> todoDtoIterable = new ArrayList<TodoDto>();
        for (Todo todo : this.repository.findAll()) {
            todoDtoIterable.add(new TodoDto(todo));
        }
        return todoDtoIterable;
    }

    @GetMapping("{id}")
    public TodoDto getTodoById(@PathVariable Long id) {
        return new TodoDto(this.repository.findById(id).get());
    }

    @PatchMapping("{id}")
    public TodoDto patchUpdatesTodoById(@PathVariable Long id, @RequestBody TodoDto todoDto) {
        Todo currentTodo = this.repository.findById(id).get();
        currentTodo.patch(todoDto);
        currentTodo = this.repository.save(currentTodo);
        return new TodoDto(currentTodo);
    }

    @DeleteMapping("{id}")
    public String deleteTodoById(@PathVariable Long id) {
        this.repository.deleteById(id);
        return "SUCCESS";
    }
}
