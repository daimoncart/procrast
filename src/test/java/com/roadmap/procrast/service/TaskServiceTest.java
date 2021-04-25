package com.roadmap.procrast.service;

import com.roadmap.procrast.exception.IncorrectTaskParameterException;
import com.roadmap.procrast.model.TaskDTO;
import org.junit.Test;

public class TaskServiceTest {

    @Test(expected = IncorrectTaskParameterException.class)
    public void whenNameIsTooShort_throwException() {

        TaskService taskService = new TaskService();

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1L);
        taskDTO.setName("O");
        taskDTO.setDescription("Description");

        taskService.save(taskDTO);
    }

    @Test(expected = IncorrectTaskParameterException.class)
    public void whenNameIsTooLong_throwException() {

        TaskService taskService = new TaskService();

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1L);
        taskDTO.setName("1234567890123456789012345678901234567890123456789012");
        taskDTO.setDescription("Description");

        taskService.save(taskDTO);
    }

    @Test(expected = IncorrectTaskParameterException.class)
    public void whenDescriptionIsTooShort_throwException() {

        TaskService taskService = new TaskService();

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1L);
        taskDTO.setName("One");
        taskDTO.setDescription("D");

        taskService.save(taskDTO);
    }

    @Test(expected = IncorrectTaskParameterException.class)
    public void whenDescriptionIsTooLong_throwException() {

        TaskService taskService = new TaskService();
        String longString = "1234567890123456789012345678901234567890123456789012";

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1L);
        taskDTO.setName("One");
        taskDTO.setDescription(longString+longString+longString+longString+longString);

        taskService.save(taskDTO);
    }

}