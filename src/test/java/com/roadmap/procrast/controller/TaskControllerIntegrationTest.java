package com.roadmap.procrast.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roadmap.procrast.model.Task;
import com.roadmap.procrast.model.TaskDTO;
import com.roadmap.procrast.repository.TaskRepository;
import com.roadmap.procrast.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.DataInput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerIntegrationTest {
    private Task task;
    private TaskDTO taskDTO;
    private Gson gson;
    private ObjectMapper mapper;


    @Autowired
    private TaskController controller;

    @Autowired
    private TaskService service;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mvc;

    @MockBean
    private TaskRepository repository;

    @BeforeEach
    private void prepareTask(){
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        task = new Task();
        task.setId(2);
        task.setName("Name001");
        task.setDescription("Description0001");
        task.setWorth(2);

        taskDTO = new TaskDTO();
        taskDTO.setId(2L);
        taskDTO.setName("Name001");
        taskDTO.setDescription("Description0001");
        taskDTO.setWorth(2);
        taskDTO.setRandomPriority(3);

        ExclusionStrategy strategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }

            @Override
            public boolean shouldSkipField(FieldAttributes field) {
                return field.getName().startsWith("randomPriority");
            }
        };
        gson = new GsonBuilder().setExclusionStrategies(strategy).create();
        mapper = new ObjectMapper();
    }

    @Test
    void getAllTasksAndExpectOK() throws Exception {
        given(repository.findAll())
            .willReturn(Collections.singletonList(task));

        MockHttpServletResponse response = mvc.perform(
            get("/tasks")
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        String responseString = response.getContentAsString();
        ArrayList<TaskDTO> list = mapper.readValue(responseString, ArrayList.class);
        TaskDTO newDTO = mapper.convertValue(list.get(0), TaskDTO.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(gson.toJson(newDTO)).isEqualTo(
            gson.toJson(task)
        );
    }

    @Test
    void getSpecificTaskAndExpectOK() throws Exception {
        given(repository.findById(2L))
            .willReturn(Optional.of(task));

        MockHttpServletResponse response = mvc.perform(
            get("/tasks/2")
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        String responseString = response.getContentAsString();
        TaskDTO newDTO = mapper.readValue(responseString, TaskDTO.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(gson.toJson(newDTO))
            .isEqualTo(gson.toJson(taskDTO));
    }

    @Test
    void createNewTaskAndExpectOK() throws Exception {
        given(repository.save(Mockito.any(Task.class)))
            .willReturn(task);

        MockHttpServletResponse response = mvc.perform(
            post("/tasks")
                .content(new Gson().toJson(taskDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        String responseString = response.getContentAsString();
        TaskDTO newDTO = mapper.readValue(responseString, TaskDTO.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(gson.toJson(newDTO))
            .isEqualTo(gson.toJson(taskDTO));
    }

    @Test
    void updateTaskAndExpectError() throws Exception {
        given(repository.save(task))
            .willReturn(task);
        given(repository.findById(6L))
            .willReturn(Optional.of(task));

        MockHttpServletResponse response = mvc.perform(
            put("/tasks/6")
                .content(new Gson().toJson(taskDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_ACCEPTABLE.value());
    }

    @Test
    void updateTaskAndExpectOK() throws Exception {
        given(repository.save(Mockito.any(Task.class)))
            .willReturn(task);
        given(repository.findById(2L))
            .willReturn(Optional.of(task));

        MockHttpServletResponse response = mvc.perform(
            put("/tasks")
                .content(new Gson().toJson(taskDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();


        String responseString = response.getContentAsString();
        TaskDTO newDTO = mapper.readValue(responseString, TaskDTO.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(gson.toJson(newDTO))
            .isEqualTo(gson.toJson(taskDTO));
    }

    @Test
    void deleteTaskAndExpectError() throws Exception {

        given(repository.findById(12L))
            .willReturn(Optional.empty());

        MockHttpServletResponse response = mvc.perform(
            delete("/tasks/12")
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        String responseString = response.getContentAsString();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

}