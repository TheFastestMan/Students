package ru.rail.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @Column(name = "course_name")
    private String name;

    @OneToMany(mappedBy = "course")
    private List<Student> students;

    @ManyToMany(mappedBy = "courses")
    private List<Trainer> trainers;

    public Course(Long id, String name, List<Student> students) {
        this.id = id;
        this.name = name;
        this.students = students;
    }


}
