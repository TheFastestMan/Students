package ru.rail.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.rail.entity.Course;
import ru.rail.entity.Trainer;
import ru.rail.util.ConfigurationUtil;

import java.util.List;

public class TrainerDao {
    private static final TrainerDao INSTANCE = new TrainerDao();
    private static SessionFactory sessionFactory;

    static {
        sessionFactory = ConfigurationUtil
                .configureWithAnnotatedClasses(Trainer.class, Course.class);
    }

    public static TrainerDao getInstance() {
        return INSTANCE;
    }




}
