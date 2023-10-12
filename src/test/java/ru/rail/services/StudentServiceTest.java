package ru.rail.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import ru.rail.dao.StudentDao;
import ru.rail.dto.StudentDto;
import ru.rail.entity.Course;
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
        studentDto.setName("Albert");
        studentDto.setCourseName("JDBC");

        studentService.saveStudentService(studentDto);

        try (Session session = sessionFactory.openSession()) {
            Student savedStudent = session.get(Student.class, studentDto.getId());
            assertNotNull(savedStudent);
            assertEquals("Albert", savedStudent.getName());
            assertNotNull(savedStudent.getCourse());
            assertEquals("JDBC", savedStudent.getCourse().getName());
        }
    }

    @Test
    public void testFindByNameStudentService() {
        // Preconditions: Save a student with a specific name
//        StudentDto studentDto = new StudentDto();
//        studentDto.setName("Arbi");
//        studentDto.setCourseName("Yu");
//        studentService.saveStudentService(studentDto);

        // Action: Find the student by name
        Student foundStudent = studentService.findByNameService("Arbi");

        // Assertions: Check if the found student matches the name
        assertNotNull(foundStudent, "Expected a student to be found");
        assertEquals("Arbi", foundStudent.getName(), "Student name did not match");
    }

    @Test
    public void testFindAllStudentByJavaEnterpriseCourse() {

    }

    @Test
    public void testDeleteService() {
        studentService.deleteStudentService(15L);
    }

    @AfterAll
    public static void cleanup() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }

    @Test
    public void testFindAllStudentsByCourseName() {
        StudentDto studentDto = new StudentDto();
        studentDto.setName("Ali");
        studentDto.setCourseName("Java");
        studentService.saveStudentService(studentDto);


        List<Student> javaStudents = studentService.findByCourseAllStudentsService("Java");

        assertFalse(javaStudents.isEmpty(), "Expected at least one student enrolled in the Java course");
        assertTrue(javaStudents.stream()
                .anyMatch(student -> "John".equals(student.getName())), "Expected to find student named John");

        javaStudents.forEach(student -> System.out.println(student.getName()));

    }

    @Test
    public void testUpdateStudent() {
        // Step 1: Set Up
        StudentDto initialStudent = new StudentDto();
        initialStudent.setName("Ivan");
        initialStudent.setCourseName("Course Name");
        Student savedStudent = studentService.saveStudentService(initialStudent);
        Long savedStudentId = savedStudent.getId();


        // Verify the initial state
        try (Session session = sessionFactory.openSession()) {
            Student fetchedStudent = session.get(Student.class, savedStudentId);
            assertNotNull(fetchedStudent);
            assertEquals("Ivan", fetchedStudent.getName());
        }

        // Step 2: Update
        initialStudent.setId(savedStudentId);
        initialStudent.setName("Luba");
        studentService.updateStudentService(initialStudent);

        // Step 3: Assert the changes
        try (Session session = sessionFactory.openSession()) {
            Student updatedStudent = session.get(Student.class, savedStudentId);
            assertNotNull(updatedStudent);
            assertEquals("Luba", updatedStudent.getName());
        }
    }

    @Test
    public void testDeleteStudentsByCourseWithLowPerformance() {
    }


}