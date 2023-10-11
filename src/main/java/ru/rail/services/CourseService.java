package ru.rail.services;

import org.modelmapper.ModelMapper;
import ru.rail.dao.CourseDao;

import ru.rail.dto.CourseDto;
import ru.rail.dto.StudentDto;
import ru.rail.entity.Course;
import ru.rail.entity.Student;


import java.util.List;
import java.util.stream.Collectors;

public class CourseService {

    private final CourseDao courseDao = CourseDao.getInstance();
    private static final CourseService INSTANCE = new CourseService();
    private ModelMapper modelMapper = new ModelMapper();

    public static CourseService getInstance() {
        return INSTANCE;
    }

    public Course saveCourseService(CourseDto courseDto) {
        Course course = convertCourseDtoToCourse(courseDto);
        return courseDao.saveCourse(course);
    }

    public void deleteCourseService(Long id) {
        courseDao.deleteCourse(id);
    }

    public Course convertCourseDtoToCourse(CourseDto courseDto) {
        return modelMapper.map(courseDto, Course.class);
    }
}
