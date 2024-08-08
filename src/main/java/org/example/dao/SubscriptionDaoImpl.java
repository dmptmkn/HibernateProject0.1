package org.example.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.entity.Course;
import org.example.entity.Subscription;
import org.example.util.ConnectionManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubscriptionDaoImpl implements SubscriptionDao {

    private static SubscriptionDaoImpl instance;
    private static SessionFactory sessionFactory = ConnectionManager.getConnection();

    private static final String FIND_ALL_QUERY = """
            from Subscription
            """;
    private final String EAGER_FIND_ALL_BY_COURSE_QUERY = """
               select s from Subscription s
               join fetch s.student 
               join fetch s.course
               where s.course.id = :courseId
               """;

    public static SubscriptionDaoImpl getInstance() {
        if (instance == null) {
            instance = new SubscriptionDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Subscription> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FIND_ALL_QUERY, Subscription.class).list();
        }
    }

    @Override
    public List<Subscription> getStudentInfo(Course course) {
        try (Session session = sessionFactory.openSession()) {
            Query<Subscription> query = session.createQuery(EAGER_FIND_ALL_BY_COURSE_QUERY, Subscription.class);
            query.setParameter("courseId", course.getId());
            return query.list();
        }
    }
}
