package ru.rail.services;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseServiceTest {

    private static SessionFactory sessionFactory;

    private CourseService courseService;

    @BeforeEach
    public void setup() {
        courseService = CourseService.getInstance();
    }

    @Test
    public void testModifyCourse() {
        // Here, you can write tests for the modify course functionality.
        // For example:
        // CourseDto courseDto = new CourseDto();
        // courseDto.setName("Test Course");
        // Course savedCourse = courseService.saveCourseService(courseDto);
        // assertNotNull(savedCourse);
        // assertEquals("Test Course", savedCourse.getName());
    }

    @Test
    public void testDeleteCourse() {
        courseService.deleteCourseService(3L);
        // After deletion, you might want to assert that the course no longer exists.
        // For example:
        // Course deletedCourse = courseService.findCourseById(2L);
        // assertNull(deletedCourse);
    }

}
