package com.roadmap.procrast.service;

import com.roadmap.procrast.exception.TaskNotFoundException;
import com.roadmap.procrast.model.Task;
import com.roadmap.procrast.model.TaskDTO;
import com.roadmap.procrast.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TaskRepository taskRepository;

    public List<TaskDTO> getTasks() {
        return taskRepository.findAll().stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    public TaskDTO findById(Long id) {
        var foundTask = taskRepository.findById(id);
        if (foundTask.isEmpty()) { throw new TaskNotFoundException("Task not found.");}
        return this.toDto(taskRepository.findById(id).get());
    }

    public TaskDTO save(TaskDTO taskDTO) {
        var task = taskRepository.save(this.fromDto(taskDTO));
        return this.toDto(task);
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    private TaskDTO toDto(Task task){
        var taskDTO = mapper.map(task, TaskDTO.class);
        taskDTO.setRandomPriority(new Random().nextInt(3)+1);
        return taskDTO;
    }

    private Task fromDto(TaskDTO taskDTO){
        return mapper.map(taskDTO, Task.class);
    }
}
