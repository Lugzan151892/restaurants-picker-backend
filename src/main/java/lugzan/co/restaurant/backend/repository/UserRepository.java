package lugzan.co.restaurant.backend.repository;

import lugzan.co.restaurant.backend.models.UserModel;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserModel, Integer> {
}
