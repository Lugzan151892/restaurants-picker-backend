package lugzan.co.restaurant.backend.controllers;

import lugzan.co.restaurant.backend.models.UserModel;
import lugzan.co.restaurant.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;

import lugzan.co.restaurant.backend.services.*;

@Controller
@RequestMapping(path="/api/user")
public class UserController {
    @Autowired

    private UserRepository userRepository;

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping(path="/add")
    public @ResponseBody String addNewUser (@RequestBody UserModel user) {
        System.out.println(user.getUserName() + " " + user.getId());
        UserModel userExistEmail = userRepository.findByEmail(user.getEmail());
        UserModel userExistName = userRepository.findByUserName(user.getUserName());

        ApiService apiService = new ApiService();

        if (userExistEmail != null) {
            apiService.setStatus(400);
            return apiService.createErrorResponse(ApiErrorMessageEnums.EXISTED_EMAIL, user.getEmail());
        }

        if (userExistName != null) {
            apiService.setStatus(400);
            return apiService.createErrorResponse(ApiErrorMessageEnums.EXISTED_USERNAME, user.getUserName());
        }

        String accessToken = JwtService.createJwtToken(user, user.getUserName());

        System.out.println(accessToken);

        System.out.println(JwtService.getTokenData(accessToken));

//        userRepository.save(user);

        return apiService.createSuccessResponse(user);
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<UserModel> getAllUsers() {
        return userRepository.findAll();
    }
}
