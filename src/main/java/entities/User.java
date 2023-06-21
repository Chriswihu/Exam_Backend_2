package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "users")
@NamedQuery(name = "User.deleteAllRows", query = "DELETE from User")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_name", length = 25)
    private String userName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "user_pass")
    private String userPass;

    @Column(name = "phone", length = 8)
    private String phone;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "status", length = 50)
    private String status;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.PERSIST)
    private List<Movie> movies = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Festival festival;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_roles", joinColumns = {
            @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
            @JoinColumn(name = "role_name", referencedColumnName = "role_name")})
    private List<Role> roleList = new ArrayList<>();



    public List<String> getRolesAsStrings() {
        if (roleList.isEmpty()) {
            return null;
        }
        List<String> rolesAsStrings = new ArrayList<>();
        roleList.forEach((role) -> {
            rolesAsStrings.add(role.getRoleName());
        });
        return rolesAsStrings;
    }

    public User() {
    }

    //TODO Change when password is hashed
    public boolean verifyPassword(String pw) {
        return BCrypt.checkpw(pw, userPass);
    }

    public User(String userName, String userPass) {
        this.userName = userName;
        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
    }

    public User(String userName, String userPass, String phone, String email, String status) {
        this.userName = userName;
        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.movies = new ArrayList<>();
//        this.festival = new Festival();
//        this.roleList = new ArrayList<>();
    }


    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return this.userPass;
    }
    public void setUserPass(String userPass) {
        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
        ;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public List<Role> getRoleList() {
        return roleList;
    }
    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
    public void addRole(Role userRole) {
        roleList.add(userRole);
    }

    public List<Movie> getMovies() {
        return movies;
    }
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
    public void addMovie(Movie movie) {
        this.movies.add(movie);
        movie.addUser(this);
    }

    public Festival getFestival() {
        return festival;
    }
    public void setFestival(Festival festival) {
        this.festival = festival;
    }


    @Override
    public String toString() {
        return "User{" +
                "userName = " + userName + '\n' +
                "userPass = " + userPass + '\n' +
                "phone = " + phone + '\n' +
                "email = " + email + '\n' +
                "status = " + status + '\n' +
                "movies = " + movies + '\n' +
                '}';
    }
}
