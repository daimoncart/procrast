package com.roadmap.procrast.controller;

import com.roadmap.procrast.model.TaskDTO;
import com.roadmap.procrast.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping(path="/tasks")
    public List<TaskDTO> getTasks() {
        return taskService.getTasks();
    }

}
