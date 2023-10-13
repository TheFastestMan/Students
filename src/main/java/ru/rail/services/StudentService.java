package ru.rail.services;

import org.modelmapper.ModelMapper;
import ru.rail.dao.CourseDao;
import ru.rail.dao.StudentDao;

import ru.rail.dto.StudentDto;
import ru.rail.entity.Course;
import ru.rail.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private static final StudentService INSTANCE = new StudentService();
    private final StudentDao studentDao = StudentDao.getInstance();
    private final CourseDao courseDao = CourseDao.getInstance();
    private ModelMapper modelMapper = new ModelMapper();

    public static StudentService getInstance() {
        return INSTANCE;
    }

    private Course fetchOrCreateCourse(String courseName) {
        Course course = courseDao.findByName(courseName);

        if (course == null) {
            course = new Course(null, courseName, new ArrayList<>());
            course = courseDao.saveCourse(course);
        }
        return course;
    }

    public Student saveStudentService(StudentDto studentDto) {
        Student student = convertStudentDtoToStudent(studentDto);

        Course course = fetchOrCreateCourse(studentDto.getCourseName());
        student.setCourse(course);

        student = studentDao.saveStudent(student);
        studentDto.setId(student.getId());
        return student;
    }

    public void deleteStudentService(Long id) {
        studentDao.deleteStudent(id);
    }

    public Student findByNameService(String name) {
        return studentDao.findByName(name);
    }

    public List<Student> findByCourseAllStudentsService(String courseName) {
        return studentDao.findByCourseAllStudents(courseName);
    }

    public Student convertStudentDtoToStudent(StudentDto studentDto) {
        return modelMapper.map(studentDto, Student.class);
    }

    public Student updateStudentService(StudentDto studentDto) {
        Student existingStudent = studentDao.findById(studentDto.getId());
        if (existingStudent == null) {
            throw new RuntimeException("Course not found with id: " + studentDto.getId());
        }
        existingStudent.setName(studentDto.getName());
        return studentDao.updateStudent(existingStudent);
    }

    public void deleteStudentsWithLowPerformanceInCourse(String courseName, Integer performanceThreshold) {
        List<Student> studentsToDelete = studentDao.findStudentsWithLowPerformanceInCourse(courseName, performanceThreshold);
        for (Student student : studentsToDelete) {
            studentDao.deleteStudent(student.getId());
        }
    }


}
