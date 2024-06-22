package lugzan.co.restaurant.backend.controllers.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import lugzan.co.restaurant.backend.models.user.*;
import lugzan.co.restaurant.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import lugzan.co.restaurant.backend.services.*;

import java.util.Date;

@Controller
@RequestMapping(path="/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    private final ApiService apiService = new ApiService();

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping(path="/add")
    public @ResponseBody String addNewUser (@RequestBody SignUpRequest request) {
        UserModel userExistEmail = userRepository.findByEmail(request.getEmail());
        UserModel userExistName = userRepository.findByUserName(request.getUserName());

        if (userExistEmail != null) {
            apiService.setStatus(400);
            return apiService.createErrorResponse(ApiErrorMessageEnums.EXISTED_EMAIL, request.getEmail());
        }

        if (userExistName != null) {
            apiService.setStatus(400);
            return apiService.createErrorResponse(ApiErrorMessageEnums.EXISTED_USERNAME, request.getUserName());
        }

        UserModel newUser = new UserModel(request);

        String accessToken = JwtService.createJwtToken(newUser, newUser.getUserName());
        String refreshToken = JwtService.createJwtRefreshToken(newUser.getEmail(), newUser.getUserName());
        newUser.setRefreshToken(refreshToken);

        userRepository.save(newUser);

        return apiService.createSuccessResponse(newUser.getRegistrationData(), accessToken);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping(path="/login")
    public @ResponseBody String login (@RequestBody LoginRequest request) {
        if (userRepository.findByUserName(request.getUserName()) == null) {
            apiService.setStatus(400);
            return apiService.createErrorResponse(ApiErrorMessageEnums.USER_NOT_FOUND, request.getUserName());
        }

        UserModel user = userRepository.findByUserName(request.getUserName());


        String accessToken = JwtService.createJwtToken(user, user.getUserName());
        String refreshToken = JwtService.createJwtRefreshToken(user.getEmail(), user.getUserName());

        user.setRefreshToken(refreshToken);

        userRepository.save(user);

        return apiService.createSuccessResponse(user.getRegistrationData(), accessToken);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping(path="/checkLogin")
    public @ResponseBody String checkLogin (@RequestHeader("Authorization") String token) {
        Claims tokenData = JwtService.getTokenData(token.substring(7));

        DecodedJWT jwt = JWT.decode(token);

        if (jwt.getExpiresAt().before(new Date())) {
            apiService.setStatus(400);
            return apiService.createErrorResponse(ApiErrorMessageEnums.TOKEN_EXPIRED, "");
        }

        String userName = tokenData.getSubject();
        UserModel user = userRepository.findByUserName(userName);

        if (user == null) {
            apiService.setStatus(400);
            return apiService.createErrorResponse(ApiErrorMessageEnums.USER_NOT_FOUND, userName);
        }

        String accessToken = JwtService.createJwtToken(user, user.getUserName());

        return apiService.createSuccessResponse(user.getAuthData(), accessToken);
    }





    @GetMapping(path = "/all")
    public @ResponseBody Iterable<UserModel> getAllUsers() {
        return userRepository.findAll();
    }
}
