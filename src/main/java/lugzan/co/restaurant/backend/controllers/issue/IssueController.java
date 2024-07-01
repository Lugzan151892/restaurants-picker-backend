package lugzan.co.restaurant.backend.controllers.issue;

import io.jsonwebtoken.Claims;
import lugzan.co.restaurant.backend.controllers.user.UserController;
import lugzan.co.restaurant.backend.models.issue.IssueCreateModel;
import lugzan.co.restaurant.backend.models.issue.IssueListModel;
import lugzan.co.restaurant.backend.models.issue.IssueModel;
import lugzan.co.restaurant.backend.models.user.UserModel;
import lugzan.co.restaurant.backend.repository.IssueRepository;
import lugzan.co.restaurant.backend.repository.UserRepository;
import lugzan.co.restaurant.backend.services.ApiErrorMessageEnums;
import lugzan.co.restaurant.backend.services.ApiService;
import lugzan.co.restaurant.backend.services.JwtService;
import lugzan.co.restaurant.backend.services.issue.enums.IssueStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/rest/issue")
public class IssueController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IssueRepository issueRepository;
    final private ApiService apiService = new ApiService();
    final private UserController userController = new UserController();

    @PostMapping(path = "/create")
    public @ResponseBody String create(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody IssueRequest issue) {

        String tokenError = JwtService.getTokenError(token);

        if (!tokenError.isEmpty()) {
            apiService.setStatus(400);
            return apiService.createMessageResponse(tokenError);
        }

        Claims tokenData = JwtService.getTokenData(JwtService.getSubToken(token));
        String userName = tokenData.getSubject();
        UserModel user = userRepository.findByUserName(userName);

        if (user == null) {
            apiService.setStatus(400);
            return apiService.createErrorResponse(ApiErrorMessageEnums.USER_NOT_FOUND, userName);
        }

        if (
            issue.getTitle() == null || issue.getTitle().isEmpty()
            || issue.getDescription() == null || issue.getDescription().isEmpty()
        ) {
            apiService.setStatus(400);
            return apiService.createMessageResponse("Title and description are required");
        }

        IssueModel newIssue = new IssueModel(issue.getTitle(), issue.getDescription(), issue.getPriority(), user);
        issueRepository.save(newIssue);
        IssueCreateModel data = new IssueCreateModel(newIssue.getId());
        apiService.setStatus(200);
        return apiService.createSuccessResponse(data);
    }

//    @PostMapping(path = "edit")
//    public @ResponseBody String edit(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody IssueRequest issue) {
//        if (token == null || token.isEmpty()) {
//            apiService.setStatus(400);
//            return apiService.createErrorResponse(ApiErrorMessageEnums.TOKEN_INCORRECT, "");
//        }
//    }

    @GetMapping(path = "/list")
    public @ResponseBody String list() {
        Iterable<IssueModel> issues = issueRepository.findAll();

        apiService.setStatus(200);
        return apiService.createSuccessResponse(new IssueListModel(issues));
    }
}
