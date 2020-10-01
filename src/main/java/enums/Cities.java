package enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum Cities {
    DELHI(1, "Delhi NCR", 28.6517178, 77.2219388, 0, "", "", false, "India", 1),
    MUMBAI(3, "Mumbai", 18.9387711, 72.8353355, 0, "", "", false, "India", 1),
    CHENNAI(7, "Chennai", 13.0801721, 80.2838331, 0, "", "", false, "India", 1),
    BANGALORE(4, "Bengaluru", 13, 77.583333, 0, "", "", false, "India", 1),
    INDIAN_ALASKA(4041, "Indian, AK", 60.9882433333, 149.5218033, 69, "AK", "Alaska", false, "United States", 216),
    NEW_YORK(988, "Syracuse, NY", 43.1561681, -75.8449946, 103, "NY", "New York State", false, "United States", 216);

    private final int cityCode;
    private final String name;
    private final double latitude;
    private final double longitude;
    private final int stateId;
    private final String stateCode;
    private final String stateName;
    private final boolean isState;
    private final String countryName;
    private final int countryId;

    private Cities(int cityCode, String name, double latitude, double longitude, int stateId, String stateCode, String stateName, boolean isState, String countryName, int countryId) {
        this.stateId = stateId;
        this.stateCode = stateCode;
        this.stateName = stateName;
        this.isState = isState;
        this.countryName = countryName;
        this.countryId = countryId;
        this.cityCode = cityCode;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
