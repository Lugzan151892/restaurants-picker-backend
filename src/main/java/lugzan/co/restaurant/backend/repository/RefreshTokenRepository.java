package lugzan.co.restaurant.backend.repository;

import lugzan.co.restaurant.backend.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
}
