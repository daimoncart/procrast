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
import java.util.Optional;
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
            .map(t -> this.toDto(t))
            .collect(Collectors.toList());
    }

//    public Task findById(Long id) {
//        Optional<Task> taskOptional = taskRepository.findById(id);
//        if (!taskOptional.isPresent()) {
//            throw new TaskNotFoundException("Task not found");
//        }
//        return taskOptional.get();
//    }

    public Task save(TaskDTO taskDTO) {
        validateTaskFieldLen(taskDTO);
        return taskRepository.save(this.fromDto(taskDTO));
    }

    public void deleteById(Long id) throws TaskNotFoundException {
        taskRepository.deleteById(id);
    }

    public TaskDTO updateTask(Long id, TaskDTO taskDTO){
        validateTaskById(id);
        validateTaskFieldLen(taskDTO);
        Task task = taskRepository.updateById(id, this.fromDto(taskDTO));
        return this.toDto(task);
    }

    private TaskDTO toDto(Task task){
        TaskDTO taskDTO = mapper.map(task, TaskDTO.class);
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
        if (!taskRepository.findById(id).isPresent()){
            throw new TaskNotFoundException("Task not found.");
        }
    }
}
