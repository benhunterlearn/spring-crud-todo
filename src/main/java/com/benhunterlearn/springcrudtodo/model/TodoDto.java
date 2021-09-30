package com.benhunterlearn.springcrudtodo.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoDto {
    private Long id;
    private String description;
    private String priority;

    @JsonProperty("due-date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dueDate;

    public TodoDto(Todo todo) {
        this.id = todo.getId();
        this.description = todo.getDescription();
        this.priority = todo.getPriority();
        this.dueDate = todo.getDueDate();
    }
}
