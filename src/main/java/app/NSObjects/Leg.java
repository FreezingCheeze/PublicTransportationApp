package app.NSObjects;

import java.util.List;

public class Leg {
    private final String idx;
    private final String origin;
    private final String destination;
    private final String name;
    private final String direction;
    private final String productNumber;
    private final String productType;
    private final String productDisplayName;
    private final List<Stop> stops;
    private final String crowdForecast;

    public Leg(String idx, String origin, String destination, String name, String direction, String productNumber, String productType, String productDisplayName, List<Stop> stops, String crowdForecast) {
        this.idx = idx;
        this.origin = origin;
        this.destination = destination;
        this.name = name;
        this.direction = direction;
        this.productNumber = productNumber;
        this.productType = productType;
        this.productDisplayName = productDisplayName;
        this.stops = stops;
        this.crowdForecast = crowdForecast;
    }

    @Override
    public String toString() {
        return "\n\tLeg{" +
                "\n\tidx='" + idx + '\'' +
                ", \n\tname='" + name + '\'' +
                ", \n\torigin='" + origin + '\'' +
                ", \n\tdestination='" + destination + '\'' +
                ", \n\tdirection='" + direction + '\'' +
                ", \n\tproductNumber='" + productNumber + '\'' +
                ", \n\tproductType='" + productType + '\'' +
                ", \n\tproductDisplayName='" + productDisplayName + '\'' +
                ", \n\tstops=" + stops +
                ", \n\tcrowdForecast='" + crowdForecast + '\'' +
                "\n\t}";
    }
}
