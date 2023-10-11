package ru.rail.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
            return student; // The student object should now have the generated ID
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback transaction on error
            }
            throw new RuntimeException("Error saving student", e); // Or any more specific exception handling you'd like
        }
    }


    public void deleteStudent(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Student student = session.get(Student.class, id); // Retrieve the student by its ID
            if (student != null) {
                session.delete(student); // Delete the student object
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback transaction on error
            }
            throw new RuntimeException("Error deleting student", e); // Or any more specific exception handling you'd like
        }
    }


}
