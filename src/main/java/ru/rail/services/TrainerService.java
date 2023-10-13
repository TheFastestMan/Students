package ru.rail.services;

import org.modelmapper.ModelMapper;
import ru.rail.dao.CourseDao;
import ru.rail.dao.TrainerDao;

import ru.rail.dto.CourseDto;
import ru.rail.dto.StudentDto;
import ru.rail.dto.TrainerDto;
import ru.rail.entity.Course;
import ru.rail.entity.Student;
import ru.rail.entity.Trainer;


import java.util.List;
import java.util.stream.Collectors;

public class TrainerService {
    private static final TrainerService INSTANCE = new TrainerService();
    private final TrainerDao trainerDao = TrainerDao.getInstance();
    private final CourseDao courseDao = CourseDao.getInstance();
    private ModelMapper modelMapper = new ModelMapper();

    public static TrainerService getInstance() {
        return INSTANCE;
    }


    public Long saveTrainerService(TrainerDto trainerDto) {
        Trainer trainer = convertTrainertDtoToTrainer(trainerDto);
        return trainerDao.saveTrainer(trainer);
    }

    public void deleteTrainerService(Long trainerId) {
        trainerDao.deleteTrainer(trainerId);
    }

    public Trainer findTrainerById(Long trainerId) {
        return trainerDao.getTrainerById(trainerId);
    }

    public Trainer convertTrainertDtoToTrainer(TrainerDto trainerDto) {
        return modelMapper.map(trainerDto, Trainer.class);
    }

    public TrainerDto saveTrainerWithCourses(TrainerDto trainerDto) {
        // Convert DTOs to entities
        Trainer trainer = modelMapper.map(trainerDto, Trainer.class);
        List<Course> courses = trainerDto.getCourses().stream()
                .map(courseDto -> {
                    if (courseDto.getId() == null) {
                        // Assuming you have a method to save a course and return the entity
                        return courseDao.saveCourse(modelMapper.map(courseDto, Course.class));
                    }
                    return modelMapper.map(courseDto, Course.class);
                })
                .collect(Collectors.toList());
        trainer.setCourses(courses);

        // Save trainer with its associated courses
        trainerDao.saveTrainer(trainer);

        // Convert back to DTO and return
        return modelMapper.map(trainer, TrainerDto.class);
    }

}
