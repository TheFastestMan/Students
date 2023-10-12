package ru.rail.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.rail.entity.Course;
import ru.rail.entity.Student;
import ru.rail.entity.StudentProfile;
import ru.rail.util.ConfigurationUtil;

public class StudentProfileDao {
    private static final StudentProfileDao INSTANCE = new StudentProfileDao();
    private static SessionFactory sessionFactory;

    static {
        sessionFactory = ConfigurationUtil
                .configureWithAnnotatedClasses(Course.class, StudentProfile.class, Student.class);
    }

    public static StudentProfileDao getInstance() {
        return INSTANCE;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public StudentProfile saveStudentProfile(StudentProfile studentProfile) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(studentProfile);
            transaction.commit();
            return studentProfile;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error saving studentProfile", e);
        }
    }

}
