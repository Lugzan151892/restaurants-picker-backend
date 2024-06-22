package lugzan.co.restaurant.backend.models.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lugzan.co.restaurant.backend.controllers.user.SignUpRequest;
import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "USERS")
public class UserModel {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column()
    private Integer id;

    @Column(unique = true)
    private String userName;

    @Column()
    protected String password;

    @Column(unique=true)
    private String email;

    @Column(unique=true, name = "refresh_token")
    protected String refreshToken;

    public UserModel() {}

    public UserModel(SignUpRequest request) {
        setUserName(request.getUserName());
        setPassword(request.getPassword());
        setEmail(request.getEmail());
    }

    public Integer getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    private String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        this.password = bCryptPasswordEncoder.encode(password);
    }

    public UserRegistrationModel getRegistrationData() {
        return new UserRegistrationModel(getId(), getUserName(), getEmail(), getRefreshToken());
    }

    public UserRegistrationModel getAuthData() {
        return new UserRegistrationModel(getId(), getUserName(), getEmail());
    }
}
