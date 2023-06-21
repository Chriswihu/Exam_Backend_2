package facades;

import dtos.MovieDTO;
import entities.Role;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import dtos.UserDTO;
import security.errorhandling.AuthenticationException;

import java.util.List;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    private static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public User getVerifiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = getEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public User addUser(String username, String password, String phone, String email, String status) {
        EntityManager em = getEntityManager();
        User user = new User(username, password, phone, email, status);
        user.addRole(new Role("user"));
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return user;
    }

    public UserDTO createUser(UserDTO ud) {
        EntityManager em = getEntityManager();
        User user = new User(ud.getUserName(), ud.getUserPass(), ud.getPhone(), ud.getEmail(), ud.getStatus());
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new UserDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        EntityManager em = getEntityManager();
        List<User> users;
        try {
            TypedQuery<User> query
                    = em.createQuery("SELECT u FROM User u", User.class);
            users = query.getResultList();
        } finally {
            em.close();
        }
        return UserDTO.getDtos(users);
    }

    public List<MovieDTO> getUserMovies(String username){
        EntityManager em = getEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.userName = :username", User.class).setParameter("username", username);
        User user = query.getSingleResult();
        return MovieDTO.getDtos(user.getMovies());
    }

    public UserDTO getUserDtoByUsername(String username) {
        EntityManager em = getEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
        } finally {
            em.close();
        }
        return new UserDTO(user);
    }

    public UserDTO editUser(String userName, UserDTO ud) {
        EntityManager em = getEntityManager();
        User user;
        try {
            user = em.find(User.class, ud.getUserName());
            user.setPhone(ud.getPhone());
            user.setEmail(ud.getEmail());
            user.setStatus(ud.getStatus());
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new UserDTO(user);
    }

    public UserDTO deleteUser(String username) {
        EntityManager em = getEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new UserDTO(user);
    }

}
