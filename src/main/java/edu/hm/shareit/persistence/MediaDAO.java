package edu.hm.shareit.persistence;

import edu.hm.hibernate.HibernateUtils;
import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * @author Carolin Direnberger
 * @author Juliane Seidl
 */
public class MediaDAO implements IMediaDAO {

    private Transaction transaction;


    private Session getSession() {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        if (transaction == null || !transaction.isActive()) {
            transaction = session.beginTransaction();
        }
        return session;
    }


    @Override
    public void addBook(Book book) {
        Session session = getSession();

        session.persist(book);

        transaction.commit();
        session.close();
    }


    @Override
    public Book getBook(String isbn) {
        Session session = getSession();

        Book book = session.get(Book.class, isbn);

        transaction.commit();
        session.close();

        return book;
    }


    @Override
    public List<Book> getBooks() {
        Session session = getSession();

        List<Book> books = session.createQuery("from Book").list();

        transaction.commit();
        session.close();

        return books;
    }


    @Override
    public void updateBook(Book book) {
        Session session = getSession();

        session.update(book);

        transaction.commit();
        session.close();
    }

    @Override
    public void addDisc(Disc disc) {
        Session session = getSession();

        session.persist(disc);

        transaction.commit();
        session.close();
    }


    @Override
    public Disc getDisc(String barcode) {
        Session session = getSession();

        Disc disc = session.get(Disc.class, barcode);

        transaction.commit();
        session.close();

        return disc;
    }


    @Override
    public List<Disc> getDiscs() {
        Session session = getSession();

        List<Disc> discs = session.createQuery("from Disc").list();

        transaction.commit();
        session.close();

        return discs;
    }

    @Override
    public void updateDisc(Disc disc) {
        Session session = getSession();

        session.update(disc);

        transaction.commit();
        session.close();
    }


}
