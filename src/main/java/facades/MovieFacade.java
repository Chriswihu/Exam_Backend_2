package facades;

import entities.Movie;
import dtos.MovieDTO;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class MovieFacade {

    private static MovieFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private MovieFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MovieFacade getMovieFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public MovieDTO create(MovieDTO movie) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return movie;
    }
    
    public List<MovieDTO> getAllMovies() {
        EntityManager em = getEntityManager();
        List<Movie> movies;
        try {
            TypedQuery<Movie> query
                    = em.createQuery("SELECT m FROM Movie m", Movie.class);
            movies = query.getResultList();
        } finally {
            em.close();
        }
        return MovieDTO.getDtos(movies);
    }

    public List<MovieDTO> getMoviesByUser(User user) {
        EntityManager em = getEntityManager();
        List<MovieDTO> movies;
        try {
            TypedQuery<MovieDTO> query
                    = em.createQuery("SELECT m FROM Movie m WHERE m JOIN m.users u WHERE u.userName = :userName", MovieDTO.class).setParameter("userName", user.getUserName());
            query.setParameter("username", user.getUserName());
            movies = query.getResultList();
        } catch (Exception e) {
            movies = null;
        } finally {
            em.close();
        }
        return movies;
    }

    public MovieDTO getMovieById(long id) {
        EntityManager em = getEntityManager();
        MovieDTO movie;
        try {
            TypedQuery<MovieDTO> query
                    = em.createQuery("SELECT m FROM Movie m WHERE m.id = :id", MovieDTO.class);
            query.setParameter("id", id);
            movie = query.getSingleResult();
        } finally {
            em.close();
        }
        return movie;
    }

    public MovieDTO editMovie(MovieDTO movie) {
        EntityManager em = getEntityManager();
        try {
            Movie movieToEdit = em.find(Movie.class, movie.getId());
            movieToEdit.setName(movie.getName());
            movieToEdit.setDuration(movie.getDuration());
            movieToEdit.setLocation(movie.getLocation());
            movieToEdit.setStartDate(movie.getStartDate());
            movieToEdit.setStartTime(movie.getStartTime());
            em.getTransaction().begin();
            em.merge(movieToEdit);
            em.getTransaction().commit();
            return new MovieDTO(movieToEdit);
        } finally {
            em.close();
        }

    }

    public MovieDTO deleteMovie(long id) {
        EntityManager em = getEntityManager();
        MovieDTO movie;
        try {
            em.getTransaction().begin();
            movie = em.find(MovieDTO.class, id);
            em.remove(movie);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return movie;
    }

}
