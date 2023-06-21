package facades;

import dtos.FestivalDTO;
import entities.Festival;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class FestivalFacade {

    private static FestivalFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private FestivalFacade() {
    }


    /**
     * @param _emf
     * @return an instance of this facade class.
     */
    public static FestivalFacade getFestivalFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new FestivalFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public FestivalDTO create(FestivalDTO festival) {
        EntityManager em = getEntityManager();
        Festival newFestival = new Festival(festival.getName(), festival.getCity(), festival.getStartDate(), festival.getDuration());
        try {
            em.getTransaction().begin();
            em.persist(newFestival);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new FestivalDTO(newFestival);
    }

    public List<FestivalDTO> getAllFestivals() {
        EntityManager em = getEntityManager();
        List<Festival> festivals;
        try {
            TypedQuery<Festival> query
                    = em.createQuery("SELECT f FROM Festival f", Festival.class);
            festivals = query.getResultList();
        } finally {
            em.close();
        }
        return FestivalDTO.getDtos(festivals);
    }

    public FestivalDTO getById(long id) { //throws RenameMeNotFoundException {
        EntityManager em = emf.createEntityManager();
        FestivalDTO festival = em.find(FestivalDTO.class, id);
        return festival;
    }

    public FestivalDTO editFestival(FestivalDTO festival) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(festival);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return festival;
    }

    public FestivalDTO deleteFestival(long id) {
        EntityManager em = emf.createEntityManager();
        FestivalDTO festival = em.find(FestivalDTO.class, id);
        try {
            em.getTransaction().begin();
            em.remove(festival);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return festival;
    }


}
