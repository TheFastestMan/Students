package ru.rail.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import ru.rail.entity.Course;
import ru.rail.entity.Student;
import ru.rail.entity.StudentProfile;
import ru.rail.util.ConfigurationUtil;

public class CourseDao {
    private static final CourseDao INSTANCE = new CourseDao();
    private static SessionFactory sessionFactory;

    public static CourseDao getInstance() {
        return INSTANCE;
    }

    public static void initializeSessionFactory() {
        sessionFactory = ConfigurationUtil
                .configureWithAnnotatedClasses(Course.class, StudentProfile.class, Student.class);
    }

    static {
        initializeSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Course saveCourse(Course course) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(course);
            transaction.commit();
            return course;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error saving course", e);
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

    public void deleteCourse(Long id) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Course course = session.get(Course.class, id);

            if (course != null) {
                for (Student student : course.getStudents()) {
                    student.setCourse(null);
                    session.update(student);
                }
                session.delete(course);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error deleting course", e);
        }
    }

    public Course findById(Long courseId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Course.class, courseId);
        }
    }

    public Course updateCourse(Course course) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(course);
            transaction.commit();
            return course;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error updating course", e);
        }
    }


}
