package app.NSObjects;

import java.util.List;

public class Leg {
    private String idx;
    private String origin;
    private String destination;
    private String name;
    private String direction;

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductDisplayName() {
        return productDisplayName;
    }

    public void setProductDisplayName(String productDisplayName) {
        this.productDisplayName = productDisplayName;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public String getCrowdForecast() {
        return crowdForecast;
    }

    public void setCrowdForecast(String crowdForecast) {
        this.crowdForecast = crowdForecast;
    }

    private String productNumber;
    private String productType;
    private String productDisplayName;
    private List<Stop> stops;
    private String crowdForecast;

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

    public String getArrivalTime(){
        return stops.get(stops.size()-1).getPlannedArrivalDateTime().toString();
    }

    public String getDepTime(){
        return stops.get(0).getPlannedDepartureDateTime().toString();
    }
}
