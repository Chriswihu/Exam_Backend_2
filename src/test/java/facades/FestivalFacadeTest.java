package facades;

import dtos.FestivalDTO;
import entities.Festival;
import entities.User;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class FestivalFacadeTest {

    private static EntityManagerFactory emf;
    private static FestivalFacade facade;
    private static EntityManager em;

    public FestivalFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = FestivalFacade.getFestivalFacade(emf);
        em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();


            Festival festival1 = new Festival("Festival1", "New Town E", "2020-01-02", 2);
            Festival festival2 = new Festival("Festival2", "New Town W", "2020-01-02", 3);

            User user1 = new User("User1", "Password1", "12345678", "Mail1", "ok");
            User user2 = new User("User2", "Password2", "12345678", "Mail2", "ok");

            festival1.addUser(user1);
            festival2.addUser(user2);


            em.persist(user1);
            em.persist(user2);

            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error in setup: " + e.getMessage());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @AfterAll
    public static void tearDownClass() {
//        em = emf.createEntityManager();
//        try {
//            em.getTransaction().begin();
//            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
//            em.createNamedQuery("User.deleteAllRows").executeUpdate();
//            em.getTransaction().commit();
//        } catch (Exception e) {
//            System.out.println("Error in tearDown: " + e.getMessage());
//            em.getTransaction().rollback();
//        } finally {
//            em.close();
//        }
    }


    @BeforeEach
    public void setUp() {
//        em = emf.createEntityManager();
    }

    @AfterEach
    public void tearDown() {
//        em = emf.createEntityManager();
    }

    @Test
    public void getFestivalByIdTest(){
//        tearDown();
        assertEquals("Festival1", facade.getById(2).getName());

        System.out.println("Festival1 er fundet: ");
        System.out.println(facade.getById(1));
    }



    @Test
    public void getAllFestivalsTest() {

        assertEquals(2, facade.getAllFestivals().size());

        System.out.println("Alle festivals er fundet: ");
        facade.getAllFestivals().forEach(System.out::println);
    }

    // TODO: Delete or change this method 
    @Test
    public void createFestivalTest() {

        Festival festival = new Festival();
        festival.setName("Festival3");
        festival.setCity("New Town S");
        festival.setStartDate("2020-01-01");
        festival.setDuration(5);


        facade.create(new FestivalDTO(festival));

        assertEquals(3, facade.getAllFestivals().size());

        System.out.println("Festival3 er tilføjet" + "\n");
        facade.getAllFestivals().forEach(System.out::println);
    }

//    @Test
//    public void deleteFestivalTest() {
//
//        facade.deleteFestival(2);
//
//        assertEquals(2, facade.getAllFestivals().size());
//
//        System.out.println("kun Festival1 og Festival3 er tilbage");
//        facade.getAllFestivals().forEach(System.out::println);
//
//
//    }

//    @Test
//    public void getMovieByUser(){
//        User user3 = new User("User3", "Password3", "12345678", "Mail3", "ok");
//        Movie movie3 = new Movie("Movie3", "3 hours", "Location3", "2020-01-01", "13:00");
//        user3.addMovie(movie3);
//
//        facade.create(new MovieDTO(movie3));
//
//        facade.getMoviesByUser(user3);
//
//        assertEquals(1, facade.getMoviesByUser(user3).size());
//    }







}
