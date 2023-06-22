package rest;

import entities.Movie;
import entities.RenameMe;
import entities.Role;
import entities.User;
import facades.MovieFacade;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class MovieResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private Movie m1, m2;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    private static MovieFacade facade;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        facade = MovieFacade.getMovieFacade(emf);

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            User user1 = new User("User1", "Password1", "12345678", "Mail1", "ok");
            User user2 = new User("User2", "Password2", "12345678", "Mail2", "ok");

//            Role adminRole = new Role("admin");

//            user2.addRole(adminRole);


            m1 = new Movie("Movie1", "3 hours", "Location1", "2020-01-01", "11:00");
            m2 = new Movie("Movie2", "2 hours", "Location2", "2020-01-02", "12:00");

            user1.addMovie(m1);
            user2.addMovie(m2);

            em.persist(user1);
            em.persist(user2);

            em.getTransaction().commit();
        } finally {
            em.close();

        }
    }

    private static String securityToken;

    private static void login(String username, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", username, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        //System.out.println("TOKEN ---> " + securityToken);
    }

    @Test
    public void testServerIsUp() {
        given().when().get("/movie").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void testGetAllMovies() throws Exception {
        given()
                .contentType("application/json")
                .get("/movie/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(2));
    }

//    @Test
//    public void testGetMovieById() throws Exception {
//        String expected = m1.getName();
//        String actual = "";
//        try {
//            actual = given()
//                    .contentType("application/json")
//                    .get("/movie/" + m1.getId()).then()
//                    .assertThat()
//                    .statusCode(HttpStatus.OK_200.getStatusCode())
//                    .extract()
//                    .path("name");
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }

    @Test
    public void createMovie() throws Exception {
        login("User1", "Password1");
        String json = String.format("{name: \"%s\", duration: \"%s\", location: \"%s\", date: \"%s\", time: \"%s\"}", "Movie3", "2 hours", "Location3", "2020-01-03", "13:00");
        try {
            given()
                    .contentType("application/json")
                    .header("x-access-token", securityToken)
                    .body(json)
                    .when().post("/movie")
                    .then()
                    .statusCode(200)
                    .body("name", equalTo("Movie3"));
        } catch (Exception e) {
            System.out.println("An error happened while Creating a Movie: " + e.getMessage());

        }
    }

    @Test
    public void deletMovie() throws Exception {
        login("User1", "Password1");
        String json = String.format("{name: \"%s\", duration: \"%s\", location: \"%s\", date: \"%s\", time: \"%s\"}", "Movie3", "2 hours", "Location3", "2020-01-03", "13:00");
        try {
            given()
                    .contentType("application/json")
                    .header("x-access-token", securityToken)
                    .body(json)
                    .when().delete("/movie/" + m1.getId())
                    .then()
                    .statusCode(200)
                    .body("name", equalTo("Movie1"));
        } catch (Exception e) {
            System.out.println("An error happened while Deleting a Movie: " + e.getMessage());

        }
    }
}
