package ru.job4j.todo.repository.task;

import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@JBossLog
@Repository
@AllArgsConstructor
public class HibernateTaskRepository implements TaskRepository {
    private final SessionFactory sf;

    @Override
    public Optional<Task> save(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.persist(task);
            session.getTransaction().commit();
            return Optional.of(task);
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Task> findById(Long id) {
        try (Session session = sf.openSession()) {
            return session.createQuery("FROM Task WHERE id = :fId", Task.class)
                    .setParameter("fId", id)
                    .uniqueResultOptional();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<Task> findAll() {
        try (Session session = sf.openSession()) {
            return session.createQuery("FROM Task", Task.class).list();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public Collection<Task> findByCompleted(boolean completed) {
        try (Session session = sf.openSession()) {
            return session.createQuery("FROM Task WHERE completed = :fCompleted", Task.class)
                    .setParameter("fCompleted", completed)
                    .list();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean update(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            int updated = session.createQuery("UPDATE Task SET name = :fName, description = :fDescription, completed = :fCompleted WHERE id = :fId")
                    .setParameter("fName", task.getName())
                    .setParameter("fDescription", task.getDescription())
                    .setParameter("fCompleted", task.getCompleted())
                    .setParameter("fId", task.getId())
                    .executeUpdate();
            session.getTransaction().commit();
            return updated > 0;
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public boolean setCompleted(Long id, boolean completed) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            int updated = session.createQuery("UPDATE Task SET completed = :fCompleted WHERE id = :fId")
                    .setParameter("fId", id)
                    .setParameter("fCompleted", completed)
                    .executeUpdate();
            session.getTransaction().commit();
            return updated > 0;
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            int deleted = session.createQuery("DELETE FROM Task WHERE id = :fId")
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
        return  false;
    }
}
