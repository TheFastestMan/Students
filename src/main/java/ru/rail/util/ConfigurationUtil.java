package ru.rail.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ConfigurationUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory configureWithAnnotatedClasses(Class<?>... annotatedClasses) {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            for (Class<?> annotatedClass : annotatedClasses) {
                configuration.addAnnotatedClass(annotatedClass);
            }
            sessionFactory = configuration.buildSessionFactory();
            return sessionFactory;
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Failed to create sessionFactory object." + e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
