package app.NSObjects;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Alert {
    @Getter @Setter
    private final String origin;
    @Getter @Setter
    private final String name;
    @Getter @Setter
    private final String arrivalTime;
    @Getter @Setter
    private final String track;

    public Alert(String origin, String name, String arrivalTime, String track) {
        this.origin = origin;
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.track = track;
    }
}
