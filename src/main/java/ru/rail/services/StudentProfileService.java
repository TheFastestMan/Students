package ru.rail.services;

import org.modelmapper.ModelMapper;
import ru.rail.dao.CourseDao;
import ru.rail.dao.StudentDao;
import ru.rail.dao.StudentProfileDao;

import ru.rail.dto.StudentDto;
import ru.rail.dto.StudentProfileDto;
import ru.rail.entity.Student;
import ru.rail.entity.StudentProfile;

public class StudentProfileService {
    private static final StudentProfileService INSTANCE = new StudentProfileService();
    private final StudentProfileDao studentProfileDao = StudentProfileDao.getInstance();
    private ModelMapper modelMapper = new ModelMapper();

    public static StudentProfileService getInstance() {
        return INSTANCE;
    }

    public StudentProfile saveStudentProfileService(StudentProfileDto studentProfileDto) {
        StudentProfile studentProfile = convertStudentProfileDtoToStudentProfile(studentProfileDto);
        StudentProfile savedProfile = studentProfileDao.saveStudentProfile(studentProfile);
        studentProfileDto.setId(savedProfile.getId());
        return savedProfile;
    }

    public StudentProfile convertStudentProfileDtoToStudentProfile(StudentProfileDto studentProfileDto) {
        StudentProfile studentProfile = modelMapper.map(studentProfileDto, StudentProfile.class);
        StudentDao studentDao = StudentDao.getInstance();
        Student student = studentDao.findByName(studentProfileDto.getStudentName());
        if (student == null) {
            throw new RuntimeException("Student not found with name: " + studentProfileDto.getStudentName());
        }
        studentProfile.setStudent(student);
        return studentProfile;
    }

}
