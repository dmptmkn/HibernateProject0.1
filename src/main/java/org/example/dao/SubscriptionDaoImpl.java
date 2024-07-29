package org.example.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.entity.Subscription;
import org.example.util.ConnectionManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubscriptionDaoImpl implements SubscriptionDao {

    private static SubscriptionDaoImpl instance;
    private static SessionFactory sessionFactory = ConnectionManager.getConnection();

    private static final String FIND_ALL_QUERY = """
            from Subscription
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
}
