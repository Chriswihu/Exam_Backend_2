package facades;

import dtos.MovieDTO;
import entities.Movie;
import entities.User;

import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class MovieFacadeTest {

    private static EntityManagerFactory emf;
    private static MovieFacade facade;
    private static EntityManager em;

    public MovieFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = MovieFacade.getMovieFacade(emf);
        em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();


            Movie movie1 = new Movie("Movie1", "3 hours", "Location1", "2020-01-01", "11:00");
            Movie movie2 = new Movie("Movie2", "4 hours", "Location2", "2020-01-01", "12:00");

            User user1 = new User("User1", "Password1", "12345678", "Mail1", "ok");
            User user2 = new User("User2", "Password2", "12345678", "Mail2", "ok");

            user1.addMovie(movie1);
            user2.addMovie(movie2);

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
    public void getMovieByIdTest(){
//        tearDown();
        assertEquals("Movie1", facade.getMovieById(1).getName());
    }



    @Test
    public void getAllMoviesTest() {

        assertEquals(3, facade.getAllMovies().size());
    }

    // TODO: Delete or change this method 
    @Test
    public void createMovieTest() {

        Movie movie = new Movie();
        movie.setName("Movie3");
        movie.setDuration("3 hours");
        movie.setLocation("Location3");
        movie.setStartDate("2020-01-01");
        movie.setStartTime("13:00");

        facade.create(new MovieDTO(movie));


        assertEquals(3, facade.getAllMovies().size());

        System.out.println("der er nu 3 movies i databasen");
        facade.getAllMovies().forEach(System.out::println);
    }

    @Test
    public void deleteMovieTest() {
//        Movie movie = new Movie();
//        movie.setName("Movie3");
//        movie.setDuration("3 hours");
//        movie.setLocation("Location3");
//        movie.setStartDate("2020-01-01");
//        movie.setStartTime("13:00");

//        facade.create(new MovieDTO(movie));
        facade.deleteMovie(2);

        assertEquals(2, facade.getAllMovies().size());

        System.out.println("kun movie1 og movie3 er tilbage");
        facade.getAllMovies().forEach(System.out::println);


    }

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
