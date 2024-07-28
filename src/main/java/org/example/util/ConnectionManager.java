package org.example.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConnectionManager {

    private static SessionFactory sessionFactory;

    public static SessionFactory getConnection() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            configuration.configure();
//            configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
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
