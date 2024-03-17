package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@JsonIgnoreProperties(value = {"students"})
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor


public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String color;
    @OneToMany(mappedBy = "faculty")
    @JsonManagedReference
    private List<Student> students;

    public Faculty(Long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }
}
