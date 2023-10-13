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
    private CourseService courseService;

    private TrainerService trainerService;


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
        trainerDto.setName("J");
        // set other fields for the Trainer...

        Long savedId = trainerService.saveTrainerService(trainerDto);
        assertNotNull(savedId);

        Trainer retrievedTrainer = trainerService.findTrainerById(savedId);
        assertNotNull(retrievedTrainer);
        assertEquals("J", retrievedTrainer.getName());
        // assert other fields...
    }

    @Test
    void deleteTrainerTest() {
        trainerService.deleteTrainerService(1L);
    }
}