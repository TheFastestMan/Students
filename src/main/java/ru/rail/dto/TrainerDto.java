package ru.rail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerDto {
    private Long id;
    private String name;
    private List<CourseDto> courses;  

}
