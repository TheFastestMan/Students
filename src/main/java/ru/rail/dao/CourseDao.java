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

import javax.persistence.Query;
import java.util.List;

public class CourseDao {
    private static final CourseDao INSTANCE = new CourseDao();
    private static SessionFactory sessionFactory;

    public static CourseDao getInstance() {
        return INSTANCE;
    }

    static {
        sessionFactory = ConfigurationUtil
                .configureWithAnnotatedClasses(Course.class, StudentProfile.class, Student.class);
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

    public void deleteCourse(Long id) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Course course = session.get(Course.class, id);

            if (course != null) {
                for (Student student : course.getStudents()) {
                    student.setCourse(null); // set the student's course to null
                    session.update(student);  // persist the change
                }
                session.delete(course);  // now, delete the course
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error deleting course", e);
        }
    }


}
