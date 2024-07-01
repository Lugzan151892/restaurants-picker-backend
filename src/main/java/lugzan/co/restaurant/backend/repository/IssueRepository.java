package lugzan.co.restaurant.backend.repository;

import lugzan.co.restaurant.backend.models.issue.IssueModel;
import lugzan.co.restaurant.backend.models.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<IssueModel, Integer> {
    IssueModel findByTitle(String title);
    IssueModel findByUser(UserModel user);
    IssueModel findById(int id);
}
