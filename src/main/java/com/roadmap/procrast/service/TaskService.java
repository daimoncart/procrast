package com.roadmap.procrast.service;

import com.roadmap.procrast.exception.IncorrectTaskParameterException;
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
        validateTaskById(id);
        return this.toDto(taskRepository.findById(id).get()); // Not a bug since id is validated already.
    }

    public TaskDTO save(TaskDTO taskDTO) {
        validateTaskFieldLen(taskDTO);
        var task = taskRepository.save(this.fromDto(taskDTO));
        return this.toDto(task);
    }

    public void deleteById(Long id) {
        validateTaskById(id);
        taskRepository.deleteById(id);
    }

    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        validateTaskById(id);
        validateTaskFieldLen(taskDTO);
        if (id != taskDTO.getId()){
            throw new IncorrectTaskParameterException("Ids do not match!");
        }
        var task = taskRepository.save(this.fromDto(taskDTO));
        return this.toDto(task);
    }

    private TaskDTO toDto(Task task){
        var taskDTO = mapper.map(task, TaskDTO.class);
        taskDTO.setRandomPriority(new Random().nextInt(3)+1);
        return taskDTO;
    }

    private Task fromDto(TaskDTO taskDTO){
        return mapper.map(taskDTO, Task.class);
    }

    private void validateTaskFieldLen(TaskDTO taskDTO) {
        int taskNameLen = taskDTO.getName().length();
        int taskDescriptionLen = taskDTO.getDescription().length();
        if (taskNameLen <3 || taskNameLen > 50) {
            throw new IncorrectTaskParameterException("Task name is either to long or too short.");
        }
        if (taskDescriptionLen <5 || taskDescriptionLen > 250) {
            throw new IncorrectTaskParameterException("Task description is either to long or too short.");
        }
    }

    private void validateTaskById(Long id) {
        if (taskRepository.findById(id).isEmpty()){
            throw new TaskNotFoundException("Task not found.");
        }
    }
}
