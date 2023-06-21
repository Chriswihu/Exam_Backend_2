//package entities;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Table(name = "guest")
//@NamedQuery(name = "Guest.deleteAllRows", query = "DELETE from Guest")
//public class Guest {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
//    private Long id;
//
//    @OneToOne(cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "name", referencedColumnName = "user_name")
//    private User user;
//
//    @Column(name = "phone", nullable = false)
//    private String phone;
//
//    @Column(name = "email", nullable = false)
//    private String email;
//
//    @Column(name = "status", nullable = false)
//    private String status;
//
//    @ManyToMany(mappedBy = "guests", cascade = CascadeType.PERSIST)
//    private List<Show> shows = new ArrayList<>();
//
//    @ManyToOne
//    @JoinColumn(name = "festival_id")
//    private Festival festival;
//
//    public Guest() {
//    }
//
//    public Guest(User user, String phone, String email, String status) {
//        this.user = user;
//        this.phone = phone;
//        this.email = email;
//        this.status = status;
//        this.shows = new ArrayList<>();
//    }
//
//    public Long getId() {
//        return id;
//    }
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public User getUser() {
//        return user;
//    }
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public void addShow(Show show) {
//        shows.add(show);
////        show.getGuests().add(this);
//    }
//    public List<Show> getShows() {
//        return shows;
//    }
//    public void setShows(List<Show> shows) {
//        this.shows = shows;
//    }
//
//    public Festival getFestival() {
//        return festival;
//    }
//    public void setFestival(Festival festival) {
//        this.festival = festival;
//    }
//
//    @Override
//    public String toString() {
//        return "Guest{" +
//                "id = " + id + "," + '\n' +
//                "user = " + user + "," + '\n' +
//                "phone = '" + phone + '\'' + "," + '\n' +
//                "email = '" + email + '\'' + "," + '\n' +
//                "status = '" + status + '\'' + "," + '\n' +
//                "shows = " + shows + "," + '\n' +
//                "festival = " + festival + "," + '\n' +
//                '}';
//    }
//}