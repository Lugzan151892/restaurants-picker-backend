package lugzan.co.restaurant.backend.models.issue;

public class IssueCreateModel {
    private Integer id;

    public IssueCreateModel() {}

    public IssueCreateModel(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
