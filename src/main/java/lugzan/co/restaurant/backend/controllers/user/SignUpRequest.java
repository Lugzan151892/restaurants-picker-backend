package lugzan.co.restaurant.backend.controllers.user;

public class SignUpRequest {
    private String email;
    private String userName;
    private String password;

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }
}
