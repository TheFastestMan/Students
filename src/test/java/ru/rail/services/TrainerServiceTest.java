package ru.rail.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.rail.dto.CourseDto;
import ru.rail.dto.TrainerDto;
import ru.rail.entity.Trainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TrainerServiceTest {

    private TrainerService trainerService;
    private CourseService courseService;

    @BeforeEach
    public void setup() {
        trainerService = TrainerService.getInstance();
        courseService = CourseService.getInstance();
    }

    @Test
    public void testSaveTrainerWithCourses() {
        // Create a few courses
        CourseDto course1 = new CourseDto();
        course1.setName("Java Basics");

        CourseDto course2 = new CourseDto();
        course2.setName("Advanced Java");

        List<CourseDto> courseDtos = new ArrayList<>();
        courseDtos.add(course1);
        courseDtos.add(course2);

        // Create Trainer
        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setName("John Doe");

        Trainer savedTrainer = trainerService.saveTrainerWithCoursesService(trainerDto, courseDtos);

        assertNotNull(savedTrainer);
        assertEquals(2, savedTrainer.getCourses().size());
    }
}