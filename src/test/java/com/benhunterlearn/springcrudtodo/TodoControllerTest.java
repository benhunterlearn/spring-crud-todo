package com.benhunterlearn.springcrudtodo;

import com.benhunterlearn.springcrudtodo.model.Todo;
import com.benhunterlearn.springcrudtodo.model.TodoDto;
import com.benhunterlearn.springcrudtodo.repository.TodoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class TodoControllerTest {
    @Autowired
    private MockMvc mvc;

//    private final ObjectMapper mapper = new ObjectMapper();
    // ObjectMapper needs to find the module that supports Java 8 Date types.
    private final ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    TodoRepository repository;

    @Autowired
    public TodoControllerTest(TodoRepository repository) {
        this.repository = repository;
    }

    @Test
    public void postCreateTodoListEntryWithValidData() throws Exception {
        TodoDto  todoDto = new TodoDto().setDescription("first thing")
                .setPriority("blue")
                .setDueDate(LocalDate.now());
        RequestBuilder request = MockMvcRequestBuilders.post("/todo")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(todoDto));
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.description", is(todoDto.getDescription())))
                .andExpect(jsonPath("$.priority", is(todoDto.getPriority())))
                .andExpect(jsonPath("$.due-date", is(todoDto.getDueDate().toString())));
    }

    @Test
    public void getTodoById() throws Exception {
        Todo firstTodo = this.repository.save(new Todo().setDescription("first")
                .setPriority("green")
                .setDueDate(LocalDate.now()));
        Todo secondTodo = this.repository.save(new Todo().setDescription("second")
                .setPriority("orange")
                .setDueDate(LocalDate.of(2021, Month.OCTOBER, 1)));

        RequestBuilder request = MockMvcRequestBuilders.get("/todo/" + firstTodo.getId())
                .accept(MediaType.APPLICATION_JSON);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(firstTodo.getId().intValue())))
                .andExpect(jsonPath("$.description", is(firstTodo.getDescription())))
                .andExpect(jsonPath("$.priority", is(firstTodo.getPriority())))
                .andExpect(jsonPath("$.due-date", is(firstTodo.getDueDate().toString())));
    }

    @Test
    public void getAllTodosFromDatabase() throws Exception {
        Todo firstTodo = this.repository.save(new Todo().setDescription("first")
                .setPriority("green")
                .setDueDate(LocalDate.now()));
        Todo secondTodo = this.repository.save(new Todo().setDescription("second")
                .setPriority("orange")
                .setDueDate(LocalDate.of(2021, Month.OCTOBER, 1)));

        RequestBuilder request = MockMvcRequestBuilders.get("/todo")
                .accept(MediaType.APPLICATION_JSON);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(firstTodo.getId().intValue())))
                .andExpect(jsonPath("$[0].description", is(firstTodo.getDescription())))
                .andExpect(jsonPath("$[0].priority", is(firstTodo.getPriority())))
                .andExpect(jsonPath("$[0].due-date", is(firstTodo.getDueDate().toString())))
                .andExpect(jsonPath("$[1].id", is(secondTodo.getId().intValue())))
                .andExpect(jsonPath("$[1].description", is(secondTodo.getDescription())))
                .andExpect(jsonPath("$[1].priority", is(secondTodo.getPriority())))
                .andExpect(jsonPath("$[1].due-date", is(secondTodo.getDueDate().toString())));
    }

    @Test
    public void patchUpdatesExistingTodoWithValidData() throws Exception {
        Todo firstTodo = this.repository.save(new Todo().setDescription("first")
                .setPriority("green")
                .setDueDate(LocalDate.now()));

        TodoDto patchTodoDto = new TodoDto().setDescription("new first")
                .setPriority("purple")
                .setDueDate(LocalDate.of(2021, Month.JULY, 1));

        RequestBuilder request = MockMvcRequestBuilders.patch("/todo/" + firstTodo.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(patchTodoDto));

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(firstTodo.getId().intValue())))
                .andExpect(jsonPath("$.description", is(patchTodoDto.getDescription())))
                .andExpect(jsonPath("$.priority", is(patchTodoDto.getPriority())))
                .andExpect(jsonPath("$.due-date", is(patchTodoDto.getDueDate().toString())));
    }

    @Test
    public void deleteRemovesExistingTodoWithValidId() throws Exception {
        Todo firstTodo = this.repository.save(new Todo().setDescription("first")
                .setPriority("green")
                .setDueDate(LocalDate.now()));

        RequestBuilder request = MockMvcRequestBuilders.delete("/todo/" + firstTodo.getId())
                .accept(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("SUCCESS"));
    }
}
