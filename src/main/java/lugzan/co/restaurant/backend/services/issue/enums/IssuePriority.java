package lugzan.co.restaurant.backend.services.issue.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum IssuePriority {
    URGENT(1), DEFAULT(2), UNIMPORTANT(3);

    @JsonValue
    private final int value;

    IssuePriority(int i) {
        this.value = i;
    }

    public int getVal() {
        return this.value;
    }
}
