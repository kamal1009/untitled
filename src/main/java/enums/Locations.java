package enums;

import lombok.Getter;

@Getter
public enum Locations {
    KORAMANGALA_BANGALORE("zone", 109253, "Koramangala, Bangalore", 12.937254, 77.626938, 4, "Bengaluru", 1, "India"),
    KORAMANGALA_1ST_BLOCK_BANGALORE("subzone", 162242, "Koramangala 1st Block, Bangalore", 12.927618, 77.623722, 4, "Bengaluru", 1, "India"),
    NAGPUR("city", 11791, "Nagaur", 27.199137, 73.741614, 11791, "Nagpur", 1, "India"),
    NAGARAJA_GARDEN("zomato_place", 99848, "Nagaraja Garden, Bengaluru", 12.900251654545, 77.587489439394, 4, "Bengaluru", 1, "India");

    String entityType;
    int entityId;
    String title;
    double latitude;
    double longitude;
    int cityId;
    String cityName;
    int countryId;
    String countryName;

    Locations(String entityType, int entityId, String title, double latitude, double longitude, int cityId, String cityName, int countryId, String countryName) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.countryId = countryId;
        this.entityId = entityId;
        this.entityType = entityType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.countryName = countryName;
        this.title = title;
    }
}
