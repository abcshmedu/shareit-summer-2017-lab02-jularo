package edu.hm.hibernate;

import java.math.BigDecimal;
import java.util.*;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.junit.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * These tests are intended to demonstrate basic ORM principles.
 * They are **not** correct unit-tests for the classes provided herein.
 */
public class BasicsTest {

    private static final Injector injector = Guice.createInjector(new GuiceTestModule());
    @Inject
    private SessionFactory sessionFactory;
    private Session entityManager;
    private Transaction tx;

    // dirty trick to store lecture's id for demonstration purposes
    private static long lectureId;

    public BasicsTest() {
        injector.injectMembers(this);
    }

    /**
     * Database is initialized with some data up-front.
     */
    @BeforeClass
    public static void initialize() {
        Session entityManager = injector.getInstance(SessionFactory.class).getCurrentSession();
        Transaction tx = entityManager.beginTransaction();

        Student studi = new Student("Longbottom", "Neville", "48963745");
        studi.setEmail(new EMail("neville@hogwarts.de"));
        entityManager.persist(studi);

        Phone phone = new Phone("08912345");
        entityManager.persist(phone);

        Teacher dozent = new Teacher("Lockhart", "Gilderoy", new BigDecimal(10000));
        dozent.setPhone(phone);
        entityManager.persist(dozent);

        Teacher dozent2 = new Teacher("Snape", "Severus",  new BigDecimal(15000));
//        dozent2.setPhone(phone); // causes a JPA Exception
        entityManager.persist(dozent2);

        Lecture lecture = new Lecture("Defence Against the Dark Arts");
        lecture.addTeacher(dozent);
        lecture.addTeacher(dozent2);
        entityManager.persist(lecture);
        lectureId = lecture.id;

        Lecture lecture2 = new Lecture("Potions");
        lecture2.addTeacher(dozent2);
        entityManager.persist(lecture2);

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
        String firstName = "Neville";
        //property does not need to be private but is case sensitive!
        String queryString = "from Person p where p.firstName='" + firstName  + "'";
        List<Person> list = entityManager.createQuery(queryString).list();
//        entityManager.getCriteriaBuilder()
        assertEquals(1, list.size());
    }

    /**
     * Demonstrates another feature of how to find entities.
     */
    @Test
    public void testFindLike() {
        String queryString = "from Person where firstName like '%evil%'";
        org.hibernate.query.Query query = entityManager.createQuery(queryString);
        List<Person> list = query.list();
        assertEquals(1, list.size());
    }

    @Test
    public void testLoadLecture() {
        Lecture lecture = (Lecture) entityManager.get(Lecture.class, lectureId);
        assertNotNull(lecture);
        List<Teacher> teachers = lecture.getTeachers();
        assertEquals(2, teachers.size());
    }

    /**
     * Same as <code>testLoadLecture</code> but with closing entityManager
     * after the find operation has completed -- and before **referenced** fields are accessed.
     */
    @Test(expected=org.hibernate.LazyInitializationException.class)
    public void testLazyLoading() {
        Lecture lecture = (Lecture) entityManager.get(Lecture.class, lectureId);
        assertNotNull(lecture);
        entityManager.close();
        List<Teacher> teachers = lecture.getTeachers();
        assertEquals(2, teachers.size());
    }

    @Test
    public void testAllPersonsWithCriteria() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> query = criteriaBuilder.createQuery(Person.class);
        CriteriaQuery<Person> root = query.select(query.from(Person.class));

        Query<Person> q = entityManager.createQuery(query);
        List<Person> persons = q.getResultList();
        assertEquals(3, persons.size());
    }

    @Test
    public void testPersonForNameCriteria() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> query = builder.createQuery(Person.class);
        Root<Person> root = query.from(Person.class);

        query.where(builder.equal(root.get("firstName"), "Neville"));

        Query<Person> q = entityManager.createQuery(query);
        List<Person> persons = q.getResultList();
        assertEquals(1, persons.size());
    }

}
