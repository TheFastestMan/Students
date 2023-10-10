package ru.rail.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import ru.rail.dao.StudentDao;
import ru.rail.dto.StudentDto;
import ru.rail.entity.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {
    private static SessionFactory sessionFactory;
    private StudentDao studentDao;
    private StudentService studentService;

    @BeforeAll
    public static void setup() {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();
    }

    @BeforeEach
    public void init() {
        studentService = StudentService.getInstance();
    }

    @Test
    public void testSaveStudentService() {
        StudentDto studentDto = new StudentDto();
        studentDto.setName("Ildus");
        studentDto.setCourseName("JDBC");

        studentService.saveStudentService(studentDto);

        try (Session session = sessionFactory.openSession()) {
            Student savedStudent = session.get(Student.class, studentDto.getId());
            assertNotNull(savedStudent);
            assertEquals("Ildus", savedStudent.getName());
            assertNotNull(savedStudent.getCourse());
            assertEquals("JDBC", savedStudent.getCourse().getName());
        }
    }

    @AfterAll
    public static void cleanup() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }


    @Test
    public void testDeleteStudentsByCourseWithLowPerformance() {
    }



}