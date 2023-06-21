package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "festival")
@NamedQuery(name = "Festival.deleteAllRows", query = "DELETE from Festival")
public class Festival {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "startDate", nullable = false) // YYYY-MM-DD (2023-06-13)
    private String startDate;

    @Column(name = "duration", nullable = false)
    private Integer duration;

//    @OneToMany(mappedBy = "festival", cascade = CascadeType.PERSIST)
//    private List<Guest> guests = new ArrayList<>();

    @OneToMany(mappedBy ="festival", cascade = CascadeType.PERSIST)
    private List<User> users;

    public Festival() {
    }

    public Festival(String name, String city, String startDate, Integer duration) {
        this.name = name;
        this.city = city;
        this.startDate = startDate;
        this.duration = duration;
        this.users = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Integer getDuration() {
        return duration;
    }
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

//    public void addGuest(Guest guest) {
//        this.guests.add(guest);
////        guest.setFestival(this);
//    }
//    public List<Guest> getGuests() {
//        return guests;
//    }
//    public void setGuests(List<Guest> guests) {
//        this.guests = guests;
//    }


    public void addUser(User user) {
        this.users.add(user);
        user.setFestival(this);
    }
    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }



    @Override
    public String toString() {
        return "Festival{" +
                "id = " + id + "," + '\n' +
                "name = '" + name + '\n' +
                "city = '" + city + '\'' +
                "startDate = '" + startDate + '\n' +
                "duration = " + duration + '\n' +
                "users = " + users +
                '}';
    }
}