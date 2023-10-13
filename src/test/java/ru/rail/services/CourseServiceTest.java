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
    public void testDeleteCourse() { // Удалить курс "Java Enterprise" // Добавить тест для удаления курса.
        courseService.deleteCourseService(19L);
    }

    @Test
    public void testSaveCourse() {
        CourseDto initialCourse = new CourseDto();
        initialCourse.setName("Java Enterprise");
        courseService.saveCourseService(initialCourse);
    }

    @Test
    public void testUpdateCourse() { //Добавить тест, для изменения курса.
        // Step 1: Set Up
//        CourseDto initialCourse = new CourseDto();
//        initialCourse.setName("Course IV()");
//        Course savedCourse = courseService.saveCourseService(initialCourse);
//        Long savedCourseId = savedCourse.getId();
        CourseDto initialCourse = courseService.findByNameService("Course IV");

        // Verify
        try (Session session = sessionFactory.openSession()) {
            Course fetchedCourse = session.get(Course.class, initialCourse.getId());
            assertNotNull(fetchedCourse);
            assertEquals("Course IV", fetchedCourse.getName());
        }

        // Step 2: Update
        initialCourse.setId(initialCourse.getId());
        initialCourse.setName("Course IV (Updated)");
        courseService.updateCourseService(initialCourse);

        // Step 3: Assert
        try (Session session = sessionFactory.openSession()) {
            Course updatedCourse = session.get(Course.class, initialCourse.getId());
            assertNotNull(updatedCourse);
            assertEquals("Course IV (Updated)", updatedCourse.getName());
        }
    }

}
