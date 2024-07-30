package org.example.util;

import lombok.experimental.UtilityClass;
import org.example.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class ConnectionManager {

    private static SessionFactory sessionFactory;

    public static SessionFactory getConnection() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            configuration.configure();
            configuration.addAnnotatedClass(Course.class);
            configuration.addAnnotatedClass(Purchase.class);
            configuration.addAnnotatedClass(Student.class);
            configuration.addAnnotatedClass(Subscription.class);
            configuration.addAnnotatedClass(Teacher.class);
            sessionFactory = configuration.buildSessionFactory();
        }

        return sessionFactory;
    }
}
