package lugzan.co.restaurant.backend.models.user;

public class UserRegistrationModel {

    private Integer id;
    private String userName;
    private String email;
    private String refreshToken;

    public UserRegistrationModel() {}

    public UserRegistrationModel(Integer id, String userName, String email, String refreshToken) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.refreshToken = refreshToken;
    }

    public UserRegistrationModel(Integer id, String userName, String email) {
        this.id = id;
        this.userName = userName;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
