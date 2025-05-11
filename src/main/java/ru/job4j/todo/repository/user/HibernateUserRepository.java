package ru.job4j.todo.repository.user;

import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;

@JBossLog
@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {
    private final SessionFactory sf;

    @Override
    public boolean save(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (Session session = sf.openSession()) {
            return session.createQuery("FROM User WHERE email = :fEmail AND password = :fPassword", User.class)
                    .setParameter("fEmail", email)
                    .setParameter("fPassword", password)
                    .uniqueResultOptional();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(int id) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            int deleted = session.createQuery("DELETE FROM User WHERE id = :fId", User.class)
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
            return deleted > 0;
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return false;
    }
}
