package ibf2023.ssf.day13.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Task {

    // <input type="text" name="name">
    @NotEmpty(message="Please enter your description")
    @Size(min=10, message="Your description must be minimally 10 characters")
    private String taskDescription;

    // <input type="number" name="quantity">
    @Min(value = 1, message = "You must select a priority")
    private Integer priority;

    @FutureOrPresent(message = "Date cannot be in the past")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message="Please set a due date") //NotNull for objects, NotEmpty will not work here
    private LocalDate dueDate;

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String task) {
        this.taskDescription = task;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Item [task=" + taskDescription + ", priority=" + priority + ", dueDate=" + dueDate + "]";
    }  
}