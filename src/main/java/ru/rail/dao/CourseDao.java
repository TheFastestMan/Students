package ru.rail.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import ru.rail.entity.Course;
import ru.rail.util.ConfigurationUtil;

import javax.persistence.Query;
import java.util.List;

public class CourseDao {
    private static final CourseDao INSTANCE = new CourseDao();
    private static SessionFactory sessionFactory;

    public static CourseDao getInstance() {
        return INSTANCE;
    }

    static {
        sessionFactory = ConfigurationUtil.configureWithAnnotatedClass(Course.class);
    }

    public Course saveCourse(Course course) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(course);
            transaction.commit();
            return course; // Return the saved course
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback transaction on error
            }
            throw new RuntimeException("Error saving course", e); // Or any more specific exception handling you'd like
        }
    }

    public Course findByName(String courseName) {
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Course.class);
            criteria.add(Restrictions.eq("name", courseName));
            return (Course) criteria.uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Error finding course by name", e);
        }
    }


}
