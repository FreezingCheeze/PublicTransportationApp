package app;

import integrations.task.TripTask;

public class PuTraCommands {


    public String calculateTrip(
        String departure,
        String destination
    ) {
        TripTask task = new TripTask();

        return "Calculating trip";
    }
}
