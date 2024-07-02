package lugzan.co.restaurant.backend.controllers.issue;

import lugzan.co.restaurant.backend.services.issue.enums.IssuePriority;

public class IssueRequest {
    private String title;
    private String description;
    private Integer priority;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPriority() {
        return priority;
    }
}
