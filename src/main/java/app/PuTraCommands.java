package app;

import app.NSObjects.Trip;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import java.util.List;

@ShellComponent
public class PuTraCommands {

    @ShellMethod("Calculate trip\nPlease enter Departure and Destination locations")
    public String calculateTrip(
        String departure,
        String destination
//        String via,
//        String[] vehicles,
//        String[] facilities,
//        boolean wheelchair_accessible
    ) {
        NSAPI api = new NSAPI();
        List<Trip> trips = api.getTrip();



        return trips.get(0).toString();
    }
}
