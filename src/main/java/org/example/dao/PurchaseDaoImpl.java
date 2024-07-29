package org.example.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.entity.Purchase;
import org.example.util.ConnectionManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PurchaseDaoImpl implements PurchaseDao {

    private static PurchaseDaoImpl instance;
    private static SessionFactory sessionFactory = ConnectionManager.getConnection();

    private static final String FIND_ALL_QUERY = """
            from Purchase
            """;

    public static PurchaseDaoImpl getInstance() {
        if (instance == null) {
            instance = new PurchaseDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Purchase> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FIND_ALL_QUERY, Purchase.class).list();
        }
    }
}
