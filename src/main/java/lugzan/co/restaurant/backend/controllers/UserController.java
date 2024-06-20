package lugzan.co.restaurant.backend.controllers;

import com.fasterxml.jackson.databind.util.JSONPObject;
import lugzan.co.restaurant.backend.models.UserModel;
import lugzan.co.restaurant.backend.repository.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping(path="/api/user")
public class UserController {
    @Autowired

    private UserRepository userRepository;

    @PostMapping(path="/add")
    public @ResponseBody String addNewUser (@RequestBody UserModel user) {
        System.out.println(user);


        UserModel n = new UserModel();
        n.setUserName(user.getUserName());
        n.setEmail(user.getEmail());
        n.setPassword(user.getPassword());
        userRepository.save(n);

        JSONObject response = new JSONObject();
        JSONObject data = new JSONObject();

        data.put("userName", n.getUserName());
        data.put("email", n.getEmail());
        response.put("error", false);
        response.put("status", 200);
        response.put("data", data);

        return response.toString();
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<UserModel> getAllUsers() {
        return userRepository.findAll();
    }
}
