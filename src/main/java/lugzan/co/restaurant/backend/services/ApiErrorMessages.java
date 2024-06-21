package lugzan.co.restaurant.backend.services;

public class ApiErrorMessages {

    public static String getErrorMessage(ApiErrorMessageEnums type, String value) {
        switch (type) {
            case EXISTED_EMAIL -> {
                return "User with email " + value + " already exist";
            }
            case EXISTED_USERNAME -> {
                return "User with username " + value + " already exist";
            }
            default -> {
                return "Unexpected error occurred. Please try again.";
            }
        }
    }
}
