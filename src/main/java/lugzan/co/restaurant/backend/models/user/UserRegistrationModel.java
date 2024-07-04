package lugzan.co.restaurant.backend.models.user;

import lugzan.co.restaurant.backend.models.issue.IssueModel;

import java.util.List;

public class UserRegistrationModel {

    private Integer id;
    private String userName;
    private String email;
    private String refreshToken;
    private List<IssueModel> created_issues;

    public UserRegistrationModel() {}

    public UserRegistrationModel(Integer id, String userName, String email, String refreshToken) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.refreshToken = refreshToken;
    }

    public UserRegistrationModel(Integer id, String userName, String email, List<IssueModel> created_issues) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.created_issues = created_issues;
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

    public List<IssueModel> getCreated_issues() {
        return created_issues;
    }
}
