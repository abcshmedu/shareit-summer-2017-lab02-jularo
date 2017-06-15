package edu.hm.hibernate;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * These tests are intended to demonstrate basic ORM principles.
 * They are **not** correct unit-tests for the classes provided herein.
 */
public class HibernateTEst {

    private static final Injector injector = Guice.createInjector(new GuiceTestModule());
    @Inject
    private SessionFactory sessionFactory;
    private Session entityManager;
    private Transaction tx;

    // dirty trick to store lecture's id for demonstration purposes
    private static String discID;

    public HibernateTEst() {
        injector.injectMembers(this);
    }

    /**
     * Database is initialized with some data up-front.
     */
    @BeforeClass
    public static void initialize() {
        Session entityManager = injector.getInstance(SessionFactory.class).getCurrentSession();
        Transaction tx = entityManager.beginTransaction();

        Book book = new Book("TAKEOVER", "Adler Olsen", "978-3-423-21648-7");
        entityManager.persist(book);

        Disc disc = new Disc("Lucky Number Slevin", "4011976318088", "Paul McGuigan", 16);
        Disc disc2 = new Disc("The Martian", "4010232067777", "Ridley Scott", 12);
        entityManager.persist(disc);
        entityManager.persist(disc2);

        discID = disc.getBarcode();

        tx.commit();
    }

    /**
     * Shut down database after all tests have run.
     */
    @AfterClass
    public static void shutDown() {
        injector.getInstance(SessionFactory.class).close();
    }

    /**
     * Initializes a entityManager before each test.
     */
    @Before
    public void setUp() {
        entityManager = sessionFactory.getCurrentSession();
        tx = entityManager.beginTransaction();
    }

    /**
     * Close entityManager after a test.
     */
    @After
    public void tearDown() {
        // some tests might close entityManager for demo purposes
        if (entityManager.isOpen()) {
            tx.commit();
        }
    }

    /**
     * Demonstrates finding of a Person entity given the FirstName.
     */
    @Test
    public void testFind() {
        String title = "TAKEOVER";
        //property does not need to be private but is case sensitive!
        String queryString = "from Book b where b.title='" + title  + "'";
        List<Book> list = entityManager.createQuery(queryString).list();
//        entityManager.getCriteriaBuilder()
        assertEquals(1, list.size());
    }

    /**
     * Demonstrates another feature of how to find entities.
     */
    @Test
    public void testFindLike() {
        String queryString = "from Book where title like '%KEO%'";
        org.hibernate.query.Query query = entityManager.createQuery(queryString);
        List<Book> list = query.list();
        assertEquals(1, list.size());
    }

    @Test
    public void testLoadLecture() {
        Disc disc = (Disc) entityManager.get(Disc.class, discID);
        assertNotNull(disc);
        assertEquals("Lucky Number Slevin",disc.getTitle());
    }

//    /**
//     * Same as <code>testLoadLecture</code> but with closing entityManager
//     * after the find operation has completed -- and before **referenced** fields are accessed.
//     */
//    @Test(expected=org.hibernate.LazyInitializationException.class)
//    public void testLazyLoading() {
//        Lecture lecture = (Lecture) entityManager.get(Lecture.class, lectureId);
//        assertNotNull(lecture);
//        entityManager.close();
//        List<Teacher> teachers = lecture.getTeachers();
//        assertEquals(2, teachers.size());
//    }
//

//    @Test
//    public void testAllMedia() {
//        //property does not need to be private but is case sensitive!
//        String queryString = "from Book";
//        List<Book> list = entityManager.createQuery(queryString).list();
////        entityManager.getCriteriaBuilder()
//        assertEquals(3, list.size());
//    }

//    @Test
//    public void testAllPersonsWithCriteria() {
//
////        Criteria crits = createCriteria();
////        crits.createCriteria("erinnerung").add(
////                Restrictions.eq("id", erinnerungId));
////        return (T) crits.uniqueResult();
//
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Medium> query = criteriaBuilder.createQuery(Medium.class);
//        CriteriaQuery<Medium> root = query.select(query.from(Medium.class));
//
//        Query<Medium> q = entityManager.createQuery(query);
//        List<Medium> medien = q.getResultList();
//        assertEquals(2, medien.size());
//    }
//
//    @Test
//    public void testPersonForNameCriteria() {
//        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Person> query = builder.createQuery(Person.class);
//        Root<Person> root = query.from(Person.class);
//
//        query.where(builder.equal(root.get("firstName"), "Neville"));
//
//        Query<Person> q = entityManager.createQuery(query);
//        List<Person> persons = q.getResultList();
//        assertEquals(1, persons.size());
//    }

}
