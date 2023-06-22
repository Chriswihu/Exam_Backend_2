package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie")
@NamedQuery(name = "Movie.deleteAllRows", query = "DELETE from Movie ")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "duration", nullable = false)
    private String duration;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "startDate", nullable = false) // YYYY-MM-DD (2023-06-13)
    private String startDate;

    @Column(name = "startTime", nullable = false) // HH:MM (14:00)
    private String startTime;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_movie",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_name")})
    private List<User> users = new ArrayList<>();

    public Movie() {
    }

    public Movie(String name, String duration, String location, String startDate, String startTime) {
        this.name = name;
        this.duration = duration;
        this.location = location;
        this.startDate = startDate;
        this.startTime = startTime;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        users.add(user);

    }

    @Override
    public String toString() {
        return "Movie{" +
                "id = " + id +
                "name = '" + name + '\n' +
                "duration = '" + duration + '\n' +
                "location = '" + location + '\n' +
                "startDate = '" + startDate + '\n' +
                "startTime = '" + startTime + '\n' +
                "users = '" + users + '\n' +
                '}';
    }
}