package lugzan.co.restaurant.backend.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lugzan.co.restaurant.backend.controllers.user.SignUpRequest;
import lugzan.co.restaurant.backend.models.issue.IssueModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Entity
@Table(name = "USERS")
public class UserModel {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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

    @Column
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<IssueModel> created_issues;

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

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    private String getPassword() {
        return password;
    }

    public void setCreated_issues(List<IssueModel> created_issues) {
        this.created_issues = created_issues;
    }

    public List<IssueModel> getCreated_issues() {
        return created_issues;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        this.password = bCryptPasswordEncoder.encode(password);
    }

    public UserRegistrationModel getRegistrationData() {
        return new UserRegistrationModel(getId(), getUserName(), getEmail(), getRefreshToken());
    }

    public UserRegistrationModel getAuthData() {
        return new UserRegistrationModel(getId(), getUserName(), getEmail(), getCreated_issues());
    }

    public Boolean validatePassword(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(password, getPassword());
    }
}
