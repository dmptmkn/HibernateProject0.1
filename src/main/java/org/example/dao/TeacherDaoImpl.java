package org.example.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.entity.Teacher;
import org.example.util.ConnectionManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TeacherDaoImpl implements TeacherDao {

    private static TeacherDaoImpl instance;
    private static SessionFactory sessionFactory = ConnectionManager.getConnection();

    private static final String FIND_ALL_QUERY = """
            from Teacher
            """;
    private static final String GET_ALL_NAMES_QUERY = """
            select name
            """ + FIND_ALL_QUERY;

    public static TeacherDaoImpl getInstance() {
        if (instance == null) {
            instance = new TeacherDaoImpl();
        }
        return instance;
    }

    @Override
    public void save(Teacher teacher) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            try {
                transaction.begin();
                session.persist(teacher);
                session.flush();
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<Teacher> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FIND_ALL_QUERY, Teacher.class).list();
        }
    }

    @Override
    public List<Teacher> findAllWithCourses() {
        try (Session session = sessionFactory.openSession()) {
            session.enableFetchProfile("withCourses");
            return session.createQuery(FIND_ALL_QUERY, Teacher.class).list();
        }
    }

    public Teacher findById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Teacher.class, id);
        }
    }

    @Override
    public List<String> getAllNames() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(GET_ALL_NAMES_QUERY, String.class).list();
        }
    }

    @Override
    public void delete(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            try {
                transaction.begin();
                Teacher teacher = session.find(Teacher.class, id);
                session.remove(teacher);
                session.flush();
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }
}
