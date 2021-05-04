package com.roadmap.procrast.model;

import lombok.Data;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
public class TaskDTO {
    private long id;
    @Size(min=2, max=50, message="Task name should be at least 2 chars long")
    private String name;
    @Size(min=5, max=250, message="Task description should be at least 5 chars long")
    private String description;
    @Min(1)
    @Max(13)
    private int worth;
    @Min(1)
    @Max(3)
    private int randomPriority;
}
