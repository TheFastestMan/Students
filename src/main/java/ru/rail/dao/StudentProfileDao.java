package ru.rail.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.rail.entity.Course;
import ru.rail.entity.Student;
import ru.rail.entity.StudentProfile;
import ru.rail.util.ConfigurationUtil;

public class StudentProfileDao {
    private static SessionFactory sessionFactory;

    static {
        sessionFactory = ConfigurationUtil
                .configureWithAnnotatedClasses(Course.class, StudentProfile.class, Student.class);
    }

}
