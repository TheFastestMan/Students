package ru.rail.services;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import ru.rail.dao.StudentProfileDao;
import ru.rail.dao.TrainerDao;
import ru.rail.dto.CourseDto;
import ru.rail.dto.TrainerDto;
import ru.rail.entity.Trainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrainerServiceTest {
    private static SessionFactory sessionFactory;
    private CourseService courseService = CourseService.getInstance();

    private TrainerService trainerService = TrainerService.getInstance();


    @BeforeAll
    public static void setup() {
        sessionFactory = StudentProfileDao.getSessionFactory();
    }

    @BeforeEach
    public void setUp() {
        courseService = CourseService.getInstance();
        trainerService = TrainerService.getInstance();
    }

    @AfterAll
    static void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
    @Test
    void saveTrainerTest() {
        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setName("Den");
        // set other fields for the Trainer...

        Long savedId = trainerService.saveTrainerService(trainerDto);
        assertNotNull(savedId);

        Trainer retrievedTrainer = trainerService.findTrainerById(savedId);
        assertNotNull(retrievedTrainer);
        assertEquals("Den", retrievedTrainer.getName());
        // assert other fields...
    }

    @Test
    void deleteTrainerTest() {
        trainerService.deleteTrainerService(1L);
    }

    @Test
    public void testSaveTrainerWithCoursesUsingDTO() {
        // Create some course DTOs
        CourseDto courseDto1 = new CourseDto(null, "Course III");
        CourseDto courseDto2 = new CourseDto(null, "Course IV");

        // Save courses and update DTOs with returned IDs
        courseDto1.setId(courseService.saveCourseService(courseDto1).getId());
        courseDto2.setId(courseService.saveCourseService(courseDto2).getId());

        List<CourseDto> courseDtos = Arrays.asList(courseDto1, courseDto2);

        // Create trainer DTO with course DTOs
        TrainerDto trainerDto = new TrainerDto(null, "Trainer II", courseDtos);

        // Save trainer with courses
        TrainerDto savedTrainerDto = trainerService.saveTrainerWithCourses(trainerDto);

        // Fetch trainer from database and verify
        Trainer retrievedTrainer = trainerService.findTrainerById(savedTrainerDto.getId());
        assertNotNull(retrievedTrainer);
        assertEquals("Trainer II", retrievedTrainer.getName());
        assertEquals(2, retrievedTrainer.getCourses().size());
    }


}