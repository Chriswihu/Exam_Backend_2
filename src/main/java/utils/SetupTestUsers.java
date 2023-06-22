package utils;


import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SetupTestUsers {

  public static void main(String[] args) {

    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();
    
    // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
    // CHANGE the three passwords below, before you uncomment and execute the code below
    // Also, either delete this file, when users are created or rename and add to .gitignore
    // Whatever you do DO NOT COMMIT and PUSH with the real passwords

    User user = new User("user", "test123", "12345678", "Mail.com", "OK");
    User admin = new User("admin", "test123", "23456789", "Mail.com", "OK");
    User both = new User("user_admin", "test123", "34567890", "Mail.com", "OK");

    if(admin.getUserPass().equals("test")||user.getUserPass().equals("test")||both.getUserPass().equals("test"))
      throw new UnsupportedOperationException("You have not changed the passwords");

    Role userRole = new Role("user");
    Role adminRole = new Role("admin");

    user.addRole(userRole);
    admin.addRole(adminRole);
    both.addRole(userRole);
    both.addRole(adminRole);

    Movie movie1 = new Movie("Show1", "3 timer", "Bykøbing Øst", "11/11-11", "11:00");
    Movie movie2 = new Movie("Show2", "2 timer", "Bykøbing Vest", "12/11-11", "13:00");
    Movie movie3 = new Movie("Show3", "4 timer", "Bykøbing Syd", "13/11-11", "12:00");
    Movie movie4 = new Movie("Show4", "1 timer", "Bykøbing Nord", "14/11-11", "14:00");

    user.addMovie(movie1);
    user.addMovie(movie2);
    user.addMovie(movie3);
    admin.addMovie(movie2);
    admin.addMovie(movie3);
    admin.addMovie(movie4);
    both.addMovie(movie3);
    both.addMovie(movie4);

    Festival festival1 = new Festival("Festival1", "Bykøbing Øst", "11/11-11", 2);
    Festival festival2 = new Festival("Festival2", "Bykøbing Vest", "12/11-11", 3);
    Festival festival3 = new Festival("Festival3", "Bykøbing Syd", "13/11-11", 4);
    Festival festival4 = new Festival("Festival4", "Bykøbing Nord", "14/11-11", 5);

    festival1.addUser(user);
    festival2.addUser(user);
    festival2.addUser(admin);
    festival3.addUser(admin);
    festival3.addUser(both);
    festival4.addUser(both);


    em.getTransaction().begin();

    em.persist(user);
    em.persist(admin);
    em.persist(both);

    em.getTransaction().commit();
    System.out.println("PW: " + user.getUserPass());
    System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
    System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
    System.out.println("Created TEST Users");
   
  }

}
