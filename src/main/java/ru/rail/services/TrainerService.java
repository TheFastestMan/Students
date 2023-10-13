package ru.rail.services;

import org.modelmapper.ModelMapper;
import ru.rail.dao.TrainerDao;

import ru.rail.dto.CourseDto;
import ru.rail.dto.TrainerDto;
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

    public Trainer saveTrainerWithCoursesService(TrainerDto trainerDto, List<CourseDto> courseDtos) {
        Trainer trainer = modelMapper.map(trainerDto, Trainer.class);
        return trainerDao.saveTrainer(trainer);
    }


}
