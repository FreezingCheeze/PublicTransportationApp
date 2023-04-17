package app.NSObjects;
import java.text.*;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.*;
public class Stop {
    private String uicCode;

    public String getUicCode() {
        return uicCode;
    }

    public String getName() {
        return name;
    }

    public Date getPlannedArrivalDateTime() {
        return plannedArrivalDateTime;
    }

    public Date getPlannedDepartureDateTime() {
        return plannedDepartureDateTime;
    }

    private String name;
    private Date plannedArrivalDateTime; // Make LocalDateTime type
    private Date plannedDepartureDateTime; // always timezone offset of 60?
    private final static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss'+'z");
    private final static SimpleDateFormat sdf1 = new SimpleDateFormat('"'+"dd-MM-yyyy'T'HH:mm:ss'+'z" + '"');

    public Stop() {

    }

    public Stop(String uicCode, String name, String plannedArrivalDateTime, String plannedDepartureDateTime) throws ParseException {
        this.uicCode = uicCode;
        this.name = name;
        if(plannedArrivalDateTime.charAt(0)=='"') {
            this.plannedArrivalDateTime = Date.from(Instant.parse(plannedArrivalDateTime.substring(1, 20) + "Z"));
        }
        else {
            this.plannedArrivalDateTime = Date.from(Instant.parse(plannedArrivalDateTime.substring(0, 19) + "Z"));
        }
        if(plannedDepartureDateTime.charAt(0)=='"'){
            this.plannedDepartureDateTime = Date.from(Instant.parse(plannedDepartureDateTime.substring(1,20)+"Z"));

        }
        else{
            this.plannedDepartureDateTime = Date.from(Instant.parse(plannedDepartureDateTime.substring(0,19)+"Z"));

        }



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