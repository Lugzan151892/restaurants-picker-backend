package lugzan.co.restaurant.backend.models.issue;

public class IssueListModel {

    private Iterable<IssueModel> issues;

    public IssueListModel() {}

    public IssueListModel(Iterable<IssueModel> issues) {
        this.issues = issues;
    }

    public Iterable<IssueModel> getItems() {
        return issues;
    }
}
