package com.roadmap.procrast.controller;

import com.roadmap.procrast.model.TaskDTO;
import com.roadmap.procrast.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping(path="/tasks")
    public List<TaskDTO> getTasks() {
        return taskService.getTasks();
    }

    @GetMapping(path="/tasks/{id}")
    public TaskDTO findTask(@PathVariable Long id) { return taskService.findById(id); }

    @PostMapping("/tasks")
    public TaskDTO newTask(@RequestBody TaskDTO taskDTO) {
        return taskService.save(taskDTO);
    }

    @PutMapping("/tasks/{id}")
    public TaskDTO updateTask(@RequestBody TaskDTO taskDTO, @PathVariable Long id){
        return taskService.updateTask(id, taskDTO);
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteById(@PathVariable Long id) {
        taskService.deleteById(id);
    }
}
