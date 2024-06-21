package lugzan.co.restaurant.backend.repository;

import lugzan.co.restaurant.backend.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    UserModel findByEmail(String email);
    UserModel findByUserName(String userName);
}
