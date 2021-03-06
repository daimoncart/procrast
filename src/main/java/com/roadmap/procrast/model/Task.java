package com.roadmap.procrast.model;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Data
@SequenceGenerator(name="seq", initialValue=8, allocationSize=200)
public class Task {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="seq")
    private long id;

    @Column(nullable=false, length=50)
    @Size(min=2, message="Task name should be at least 2 chars long")
    private String name;

    @Column(nullable=false, length=250)
    @Size(min=5, message="Task description should be at least 5 chars long")
    private String description;

    @Column(nullable=false)
    @Min(1)
    @Max(13)
    private int worth;
}
