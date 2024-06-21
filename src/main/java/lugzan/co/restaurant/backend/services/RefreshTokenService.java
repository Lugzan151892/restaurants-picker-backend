package lugzan.co.restaurant.backend.services;

import lugzan.co.restaurant.backend.models.RefreshToken;
import lugzan.co.restaurant.backend.repository.RefreshTokenRepository;
import lugzan.co.restaurant.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public RefreshToken createToken(String userName) {
        RefreshToken
    }

}
