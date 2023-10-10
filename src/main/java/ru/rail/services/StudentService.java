package ru.rail.services;

import org.mapstruct.ap.spi.MapStructProcessingEnvironment;
import org.modelmapper.ModelMapper;
import ru.rail.dao.CourseDao;
import ru.rail.dao.StudentDao;

import ru.rail.dto.StudentDto;
import ru.rail.entity.Course;
import ru.rail.entity.Student;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentService {

    private static final StudentService INSTANCE = new StudentService();

    private final StudentDao studentDao = StudentDao.getInstance();

    private final CourseDao courseDao = CourseDao.getInstance();
    private ModelMapper modelMapper = new ModelMapper();

    public static StudentService getInstance() {
        return INSTANCE;
    }

    private Course fetchOrCreateCourse(String courseName) {
        // Try to fetch the course from the database (you'll need a method in your DAO for this)
        Course course = courseDao.findByName(courseName);

        // If the course doesn't exist, create a new one and save it
        if (course == null) {
            course = new Course(null, courseName, new ArrayList<>());
            // Here, save the course using a method in your DAO (you'll need to implement this)
            course = courseDao.saveCourse(course);
        }
        return course;
    }

    public void saveStudentService(StudentDto studentDto){
        Student student = convertStudentDtoToStudent(studentDto);

        // Fetch the course by its name or create a new one if it doesn't exist
        Course course = fetchOrCreateCourse(studentDto.getCourseName());
        student.setCourse(course);  // Set the course on the student

        student = studentDao.saveStudent(student);
        studentDto.setId(student.getId());
    }


    public Student convertStudentDtoToStudent(StudentDto studentDto) {
        return modelMapper.map(studentDto, Student.class);
    }




}
