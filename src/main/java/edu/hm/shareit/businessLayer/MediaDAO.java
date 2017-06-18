package edu.hm.shareit.businessLayer;

import edu.hm.hibernate.HibernateUtils;
import edu.hm.shareit.models.Book;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * @author Carolin Direnberger
 * @author Juliane Seidl
 */
public class MediaDAO implements IMediaDAO {

    private Session dataManager;

    private Transaction transaction;


    public MediaDAO() {
    }


    @Override
    public void createBook(Book book) {
        dataManager = HibernateUtils.getSessionFactory().getCurrentSession();
        if (transaction == null || !transaction.isActive()) {
            transaction = dataManager.beginTransaction();
        }
        dataManager.saveOrUpdate(book);
        transaction.commit();
        dataManager.close();
    }


    @Override
    public void deleteBook(Book book) {
        dataManager = HibernateUtils.getSessionFactory().getCurrentSession();
        if (transaction == null || !transaction.isActive()) {
            transaction = dataManager.beginTransaction();
        }
        dataManager.delete(book);
        dataManager.close();
    }


    @Override
    public Book getBook(String isbn) {
        dataManager = HibernateUtils.getSessionFactory().getCurrentSession();
        if (transaction == null || !transaction.isActive()) {
            transaction = dataManager.beginTransaction();
        }
        Book book = dataManager.get(Book.class, isbn);
        transaction.commit();
        dataManager.close();
        return book;
    }


    @Override
    public List<Book> getBooks() {
        dataManager = HibernateUtils.getSessionFactory().getCurrentSession();
        if (transaction == null || !transaction.isActive()) {
            transaction = dataManager.beginTransaction();
        }
        List books = dataManager.createQuery("from Book").list();
        dataManager.close();
        return books;
    }


    @Override
    public void updateBook(Book book) {
        dataManager = HibernateUtils.getSessionFactory().getCurrentSession();
        if (transaction == null || !transaction.isActive()) {
            transaction = dataManager.beginTransaction();
        }
        dataManager.close();
    }
}
