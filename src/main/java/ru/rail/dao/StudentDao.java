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


public class StudentDao {

    private static final StudentDao INSTANCE = new StudentDao();
    private static SessionFactory sessionFactory;

    public static StudentDao getInstance() {
        return INSTANCE;
    }

    static {
        sessionFactory = ConfigurationUtil
                .configureWithAnnotatedClasses(Course.class, StudentProfile.class, Student.class);

    }

    public Student saveStudent(Student student) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(student);
            transaction.commit();
            return student;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error saving student", e);
        }
    }

    public Student findByName(String studentName) {
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Student.class);
            criteria.add(Restrictions.eq("name", studentName));
            return (Student) criteria.uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Error finding course by name", e);
        }
    }

    public Student findById(Long courseId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Student.class, courseId);
        }
    }

    public void deleteStudent(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Student student = session.get(Student.class, id);
            if (student != null) {
                session.delete(student);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error deleting student", e);
        }
    }

    public Student updateStudent(Student student) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(student);
            transaction.commit();
            return student;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error updating course", e);
        }
    }

}
