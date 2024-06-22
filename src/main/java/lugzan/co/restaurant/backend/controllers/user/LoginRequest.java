package lugzan.co.restaurant.backend.controllers.user;

public class LoginRequest {
    private String userName;
    private String password;

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }
}
