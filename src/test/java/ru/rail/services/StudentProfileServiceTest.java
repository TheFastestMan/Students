package ru.rail.services;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rail.dao.StudentProfileDao;
import ru.rail.dto.CourseDto;
import ru.rail.dto.StudentDto;
import ru.rail.dto.StudentProfileDto;
import ru.rail.entity.Course;
import ru.rail.entity.Student;
import ru.rail.entity.StudentProfile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentProfileServiceTest {
    private static SessionFactory sessionFactory;
    private StudentProfileService studentProfileService;
    private CourseService courseService;
    private StudentService studentService;

    @BeforeAll
    public static void setup() {
        sessionFactory = StudentProfileDao.getSessionFactory();
    }

    @BeforeEach
    public void setUp() {
        courseService = CourseService.getInstance();
        studentService = StudentService.getInstance();
        studentProfileService = StudentProfileService.getInstance();
    }

    @Test
    public void testSaveStudentProfileService() {
        // 1. a course
        CourseDto courseDto = new CourseDto();
        courseDto.setName("Java Enterprise");
        Course savedCourse = courseService.saveCourseService(courseDto);

        // 2. a student
        StudentDto studentDto = new StudentDto();
        studentDto.setName("Lee");
        studentDto.setCourseName(savedCourse.getName());
        Student savedStudent = studentService.saveStudentService(studentDto);

        // Validate
        assertEquals(savedCourse.getName(), savedStudent.getCourse().getName());

        // 3. a student
        StudentProfileDto studentProfileDto = new StudentProfileDto();
        studentProfileDto.setStudentAcademicPerformance(7);
        studentProfileDto.setStudentName("Lee");
        StudentProfile savedStudentProfile = studentProfileService.saveStudentProfileService(studentProfileDto);

        // Validate
        assertEquals("Lee", savedStudentProfile.getStudent().getName());
        assertEquals(Integer.valueOf(7), savedStudentProfile.getAcademicPerformance());
    }


        @AfterAll
    public static void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}