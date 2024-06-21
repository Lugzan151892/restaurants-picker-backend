package lugzan.co.restaurant.backend.services;

import org.json.JSONObject;

public class ApiService {

    private int status = 200;

    public void setStatus(int status) {
        this.status = status;
    }

    private Boolean getErrorByStatus(int status) {
        return switch (status) {
            case 400, 401, 500 -> true;
            default -> false;
        };
    }

    public String createSuccessResponse(Object data) {
        JSONObject response = new JSONObject();
        response.put("error", getErrorByStatus(this.status));
        response.put("status", this.status);
        response.put("data", new JSONObject(data));

        return response.toString();
    }

    public String createErrorResponse(ApiErrorMessageEnums errorType, String value) {
        JSONObject response = new JSONObject();
        response.put("error", getErrorByStatus(this.status));
        response.put("status", this.status);
        response.put("errorMessage", ApiErrorMessages.getErrorMessage(errorType, value));

        return response.toString();
    }
}
