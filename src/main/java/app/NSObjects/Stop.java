package app.NSObjects;

public class Stop {
    private final String uicCode;
    private final String name;
    private final String plannedArrivalDateTime; // Make LocalDateTime type
    private final String plannedDepartureDateTime; // always timezone offset of 60?

    public Stop(String uicCode, String name, String plannedArrivalDateTime, String plannedDepartureDateTime) {
        this.uicCode = uicCode;
        this.name = name;
        this.plannedArrivalDateTime = plannedArrivalDateTime;
        this.plannedDepartureDateTime = plannedDepartureDateTime;
    }

    @Override
    public String toString() {
        return "\n\t\tStop{" +
                "\n\t\tuicCode='" + uicCode + '\'' +
                ", \n\t\tname='" + name + '\'' +
                ", \n\t\tplannedArrivalDateTime='" + plannedArrivalDateTime + '\'' +
                ", \n\t\tplannedDepartureDateTime='" + plannedDepartureDateTime + '\'' +
                "\n\t\t}";
    }
}