package lugzan.co.restaurant.backend.controllers.user;

import io.jsonwebtoken.Claims;
import lugzan.co.restaurant.backend.models.user.*;
import lugzan.co.restaurant.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import lugzan.co.restaurant.backend.services.*;

import java.util.Objects;

@Controller
@RequestMapping(path="/rest/user", produces = "text/plain;charset=UTF-8")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    private final static ApiService apiService = new ApiService();

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

    @PostMapping(path="/login")
    public @ResponseBody String login (@RequestBody LoginRequest request) {
        UserModel user = userRepository.findByUserName(request.getUserName());

        if (user == null) {
            apiService.setStatus(400);
            return apiService.createErrorResponse(ApiErrorMessageEnums.USER_NOT_FOUND, request.getUserName());
        }

        if (!user.validatePassword(request.getPassword())) {
            apiService.setStatus(400);
            return apiService.createErrorResponse(ApiErrorMessageEnums.PASSWORD_INCORRECT, "");
        }

        String accessToken = JwtService.createJwtToken(user, user.getUserName());
        String refreshToken = JwtService.createJwtRefreshToken(user.getEmail(), user.getUserName());
        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return apiService.createSuccessResponse(user.getRegistrationData(), accessToken);
    }

    @GetMapping(path="/checkLogin")
    public @ResponseBody String checkLogin (@RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null || token.isEmpty()) {
            apiService.setStatus(400);
            return apiService.createErrorResponse(ApiErrorMessageEnums.TOKEN_INCORRECT, "");
        }
        String subToken = token.substring(7);
        return this.handleSuccessToken(subToken, false);
    }

    @PostMapping(path="/updateToken")
    public @ResponseBody String updateAccessToken (@RequestBody RefreshToken refreshToken) {
        if (refreshToken.getToken() == null) {
            apiService.setStatus(400);
            return apiService.createErrorResponse(ApiErrorMessageEnums.TOKEN_INCORRECT, "");
        }
        return this.handleSuccessToken(refreshToken.getToken(), true);
    }

    @GetMapping(path="/logout")
    public @ResponseBody String logout(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null || token.isEmpty()) {
            apiService.setStatus(400);
            return apiService.createErrorResponse(ApiErrorMessageEnums.TOKEN_INCORRECT, "");
        }

        String subToken = token.substring(7);

        if (JwtService.isTokenExpired(subToken)) {
            apiService.setStatus(400);
            return apiService.createErrorResponse(ApiErrorMessageEnums.TOKEN_EXPIRED, "");
        }

        Claims tokenData = JwtService.getTokenData(subToken);

        String userName = tokenData.getSubject();
        UserModel user = userRepository.findByUserName(userName);

        if (user == null) {
            apiService.setStatus(400);
            return apiService.createErrorResponse(ApiErrorMessageEnums.USER_NOT_FOUND, userName);
        }

        user.setRefreshToken(null);
        userRepository.save(user);

        return apiService.createMessageResponse("Successfully logout!");
    }

    private String handleSuccessToken(String token, Boolean isRefreshToken) {
        if (JwtService.isTokenExpired(token)) {
            apiService.setStatus(400);
            return apiService.createErrorResponse(ApiErrorMessageEnums.TOKEN_EXPIRED, "");
        }

        Claims tokenData;

        if (isRefreshToken) {
            tokenData = JwtService.getRefreshTokenData(token);
        } else {
            tokenData = JwtService.getTokenData(token);
        }

        String userName = tokenData.getSubject();
        UserModel user = userRepository.findByUserName(userName);

        if (user == null) {
            apiService.setStatus(400);
            return apiService.createErrorResponse(ApiErrorMessageEnums.USER_NOT_FOUND, userName);
        }

        if (isRefreshToken && (!Objects.equals(user.getRefreshToken(), token))) {
            apiService.setStatus(400);
            return apiService.createErrorResponse(ApiErrorMessageEnums.TOKEN_INCORRECT, "");
        }

        String accessToken = JwtService.createJwtToken(user, user.getUserName());

        return apiService.createSuccessResponse(user.getAuthData(), accessToken);
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<UserModel> getAllUsers() {
        return userRepository.findAll();
    }
}
