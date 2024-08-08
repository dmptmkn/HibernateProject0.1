package org.example.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.entity.Course;
import org.example.util.ConnectionManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.BatchSize;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CourseDaoImpl implements CourseDao {

    private static CourseDaoImpl instance;
    private static SessionFactory sessionFactory = ConnectionManager.getConnection();

    private static final String FIND_ALL_QUERY = """
            from Course
            """;
    private static final String FIND_ALL_WITH_STUDENTS_QUERY = """
            from Course c join fetch c.students
            """;
    private static final String GET_ALL_NAMES_QUERY = """
            select name
            """ + FIND_ALL_QUERY;

    public static CourseDaoImpl getInstance() {
        if (instance == null) {
            instance = new CourseDaoImpl();
        }
        return instance;
    }

    @Override
    @BatchSize(size = 4)
    public List<Course> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FIND_ALL_QUERY, Course.class).list();
        }
    }

    @Override
    public void save(Course course) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            try {
                transaction.begin();
                session.persist(course);
                session.flush();
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<Course> findAllWithTeacher() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FIND_ALL_QUERY, Course.class)
                    .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("withTeacher"))
                    .list();
        }
    }

    @Override
    public List<Course> findAllWithStudents() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FIND_ALL_WITH_STUDENTS_QUERY, Course.class).list();
        }
    }

    @Override
    public List<Course> findAllWithDurationGT(Integer duration) {
        try (Session session = sessionFactory.openSession()) {
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            JpaCriteriaQuery<Course> query = builder.createQuery(Course.class);

            var root = query.from(Course.class);
            query.select(root).where(builder.gt(root.get("duration"), duration));

            return session.createQuery(query).list();
        }
    }

    @Override
    public List<Course> findAllWithPriceGT(Integer price) {
        try (Session session = sessionFactory.openSession()) {
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            JpaCriteriaQuery<Course> query = builder.createQuery(Course.class);

            var root = query.from(Course.class);
            query.select(root).where(builder.gt(root.get("price"), price));

            return session.createQuery(query).list();
        }
    }

    @Override
    public List<Course> findAllWithTeacherAgeGTAndDurationLT(Integer teacherAge, Integer duration) {
        try (Session session = sessionFactory.openSession()) {
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            JpaCriteriaQuery<Course> query = builder.createQuery(Course.class);

            var root = query.from(Course.class);
            query.select(root).where(builder.and(
                    builder.gt(root.get("teacher").get("age"), teacherAge),
                    builder.lt(root.get("duration"), duration)));

            return session.createQuery(query).list();
        }
    }

    @Override
    public List<Course> findAllWithPriceBetween(Integer from, Integer to) {
        try (Session session = sessionFactory.openSession()) {
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            JpaCriteriaQuery<Course> query = builder.createQuery(Course.class);

            var root = query.from(Course.class);
            query.select(root).where(builder.between(root.get("price"), from, to));

            return session.createQuery(query).list();
        }
    }

    @Override
    public List<Course> findAllWithTeacherAgeGTOrDurationGT(Integer teacherAge, Integer duration) {
        try (Session session = sessionFactory.openSession()) {

            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            JpaCriteriaQuery<Course> query = builder.createQuery(Course.class);

            var root = query.from(Course.class);
            query.select(root).where(builder.or(
                    builder.gt(root.get("teacher").get("age"), teacherAge),
                    builder.gt(root.get("duration"), duration)));

            return session.createQuery(query).list();
        }
    }

    @Override
    public List<Course> findFirstNWithMaxDuration(Integer limit) {
        try (Session session = sessionFactory.openSession()) {
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            JpaCriteriaQuery<Course> query = builder.createQuery(Course.class);

            var root = query.from(Course.class);
            query.select(root).orderBy(builder.desc(root.get("duration")));

            return session.createQuery(query).setMaxResults(limit).list();
        }
    }

    @Override
    public List<String> getAllNames() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(GET_ALL_NAMES_QUERY, String.class).list();
        }
    }
}
