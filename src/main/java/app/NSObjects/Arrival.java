package app.NSObjects;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Arrival {
    private String origin;
    private String name;
    private Date plannedDateTime;
    private String plannedTrack;


    public Arrival(String origin, String name, String plannedDateTime, String plannedTrack) {
        this.origin = origin;
        this.name = name;
        this.plannedTrack = plannedTrack;

        if(plannedDateTime.charAt(0)=='"') {
            this.plannedDateTime = Date.from(Instant.parse(plannedDateTime.substring(1, 20) + "Z"));
        }
        else {
            this.plannedDateTime = Date.from(Instant.parse(plannedDateTime.substring(0, 19) + "Z"));
        }
    }

    @Override
    public String toString() {
        return "Arrival{" +
                "origin='" + origin + '\'' +
                ", name='" + name + '\'' +
                ", plannedDateTime=" + plannedDateTime +
                ", plannedTrack='" + plannedTrack + '\'' +
                '}';
    }
}
