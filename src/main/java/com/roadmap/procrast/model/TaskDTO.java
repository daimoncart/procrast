package com.roadmap.procrast.model;

import lombok.Data;

@Data
public class TaskDTO {
    private long id;
    private String name;
    private String description;
    private int worth;
    private int randomPriority;
}
