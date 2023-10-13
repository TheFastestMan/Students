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

    public Trainer saveTrainer(Trainer trainer) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(trainer);
            transaction.commit();
            return trainer;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error saving trainer", e);
        }
    }

    public Trainer saveTrainerWithCourses(Trainer trainer, List<Course> courses) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            trainer.setCourses(courses);
            session.save(trainer);
            transaction.commit();
            return trainer;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error saving trainer with courses", e);
        }
    }


}
