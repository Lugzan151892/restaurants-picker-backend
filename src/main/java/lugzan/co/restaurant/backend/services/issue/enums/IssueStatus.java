package lugzan.co.restaurant.backend.services.issue.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum IssueStatus {
    WAITING(1), IN_PROGRESS(2), COMPLETED(3);

    @JsonValue
    private final int value;

    IssueStatus(int i) {
        this.value = i;
    }

    public int getVal() {
        return this.value;
    }
}
