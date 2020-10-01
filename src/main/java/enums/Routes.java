package enums;

public enum Routes {
    GET_CITIES("/cities"),
    GET_COLLECTION("/collections"),
    GET_CATEGORIES("/categories"),
    GET_CUISINE("/cuisines"),
    GET_ESTABLISHMENT("/establishments"),
    GET_LOCATION("/locations"),
    GET_LOCATION_DETAILS("/location_details"),
    SEARCH_RESTAURANT("/search"),
    GET_RESTAURANT("/restaurant"),
    GET_REVIEW("/reviews"),
    GET_RESTAURANT_NEAR_ME("/geocode");

    private String endPoint;

    public String getUrl(){
        return  endPoint;
    }

    Routes(String endPoint) {
        this.endPoint=endPoint;
    }
}
