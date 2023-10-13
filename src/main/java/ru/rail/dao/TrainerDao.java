package ru.rail.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.rail.entity.Course;
import ru.rail.entity.Trainer;
import ru.rail.util.ConfigurationUtil;

import org.hibernate.query.Query;

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

    public Long saveTrainer(Trainer trainer) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Long id = (Long) session.save(trainer);
            session.getTransaction().commit();
            return id;
        }
    }

    public void deleteTrainer(Long trainerId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Trainer trainer = session.get(Trainer.class, trainerId);
            if (trainer != null) {
                session.delete(trainer);
            }
            session.getTransaction().commit();
        }
    }

    public Trainer getTrainerById(Long trainerId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Trainer.class, trainerId);
        }
    }

    public List<Trainer> getAllTrainers() {
        try (Session session = sessionFactory.openSession()) {
            Query<Trainer> query = session.createQuery("from Trainer", Trainer.class);
            return query.list();
        }
    }
}