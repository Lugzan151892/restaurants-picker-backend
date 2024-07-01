package lugzan.co.restaurant.backend.models.issue;

import jakarta.persistence.*;
import lugzan.co.restaurant.backend.models.user.UserModel;
import lugzan.co.restaurant.backend.services.issue.enums.IssuePriority;
import lugzan.co.restaurant.backend.services.issue.enums.IssueStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Table (name = "ISSUES")
public class IssueModel {
//    id: number;
//    title: string;
//    description: string;
//    priority: EISSUE_PRIORITY;
//    created: string;
//    status: EISSUE_STATUS;
//    userId: number;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private Integer priority;

    @Column
    @CreatedDate
    private Date created;

    @Column
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserModel user;

    public IssueModel() {}

    public IssueModel(String title, String description, Integer priority, UserModel user) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.created = new Date();
        this.status = IssueStatus.WAITING.getVal();
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCreated() {
        return created;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UserModel getUser() {
        return user;
    }
}