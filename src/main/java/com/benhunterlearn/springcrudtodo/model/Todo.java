package com.benhunterlearn.springcrudtodo.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;
    private String priority;
    private LocalDate dueDate;

    public Todo(TodoDto todoDto) {
        this.description = todoDto.getDescription();
        this.priority = todoDto.getPriority();
        this.dueDate = todoDto.getDueDate();
    }

    public Todo patch(TodoDto todoDto) {
        if (todoDto.getDescription() != null) {
            this.description = todoDto.getDescription();
        }
        if (todoDto.getPriority() != null) {
            this.priority = todoDto.getPriority();
        }
        if (todoDto.getDueDate() != null) {
            this.dueDate = todoDto.getDueDate();
        }
        return this;
    }
}
