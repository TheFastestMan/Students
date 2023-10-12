package ru.rail.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rail.dao.CourseDao;
import ru.rail.dto.CourseDto;
import ru.rail.entity.Course;

import static org.junit.jupiter.api.Assertions.*;

class CourseServiceTest {

    private static SessionFactory sessionFactory;

    private CourseService courseService;

    @BeforeAll
    public static void setupSessionFactory() {
        CourseDao.initializeSessionFactory();
        sessionFactory = CourseDao.getSessionFactory();
    }


    @BeforeEach
    public void setup() {
        courseService = CourseService.getInstance();
    }


    @Test
    public void testDeleteCourse() {
        courseService.deleteCourseService(10L);
    }
    @Test
    public void testSaveCourse() {
        CourseDto initialCourse = new CourseDto();
        initialCourse.setName("New");
        courseService.saveCourseService(initialCourse);
    }

    @Test
    public void testUpdateCourse() {
        // Step 1: Set Up
        CourseDto initialCourse = new CourseDto();
        initialCourse.setName("O");
        Course savedCourse = courseService.saveCourseService(initialCourse);
        Long savedCourseId = savedCourse.getId();


        // Verify the initial state
        try (Session session = sessionFactory.openSession()) {
            Course fetchedCourse = session.get(Course.class, savedCourseId);
            assertNotNull(fetchedCourse);
            assertEquals("O", fetchedCourse.getName());
        }

        // Step 2: Update
        initialCourse.setId(savedCourseId);
        initialCourse.setName("BBB");
        courseService.updateCourseService(initialCourse);

        // Step 3: Assert the changes
        try (Session session = sessionFactory.openSession()) {
            Course updatedCourse = session.get(Course.class, savedCourseId);
            assertNotNull(updatedCourse);
            assertEquals("BBB", updatedCourse.getName());
        }
    }

}
