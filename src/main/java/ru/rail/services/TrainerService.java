package ru.rail.services;

import org.modelmapper.ModelMapper;
import ru.rail.dao.TrainerDao;

import ru.rail.dto.CourseDto;
import ru.rail.dto.StudentDto;
import ru.rail.dto.TrainerDto;
import ru.rail.entity.Student;
import ru.rail.entity.Trainer;


import java.util.List;
import java.util.stream.Collectors;

public class TrainerService {
    private static final TrainerService INSTANCE = new TrainerService();
    private final TrainerDao trainerDao = TrainerDao.getInstance();
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

}
