package dtos;

import entities.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link User}
 */
public class UserDTO implements Serializable {
    @NotNull
    private final String userName;
    @NotNull
    @Size(min = 1, max = 255)
    private final String userPass;
    private final String phone;
    private final String email;
    private final String status;

    public UserDTO(User user) {
        this.userName = user.getUserName();
        this.userPass = user.getUserPass();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.status = user.getStatus();
    }

    public static List<UserDTO> getDtos(List<User> users) {
        return users.stream()
                .map(UserDTO::new)
                .collect(java.util.stream.Collectors.toList());
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }



    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "userName = " + userName + ", " +
                "userPass = " + userPass + ", " +
                "phone = " + phone + ", " +
                "email = " + email + ", " +
                "status = " + status + ")";
    }
}