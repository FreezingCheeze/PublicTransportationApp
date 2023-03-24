package app;

import app.NSObjects.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class NSAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(NSAPI.class);

    private String scheme = "https://";
    private String trips_path = "gateway.apiportal.ns.nl/reisinformatie-api/api/v3/trips";

    private String departure = "";
    private String destination = "";
    private String viaStation = "";
    private ArrayList<String> vehicles = new ArrayList<String>();
    private ArrayList<String> facilities = new ArrayList<String>();
    private boolean wheelchair_accessible = false;

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getViaStation() {
        return viaStation;
    }

    public void setViaStation(String viaStation) {
        this.viaStation = viaStation;
    }

    public ArrayList<String> getVehicles() {
        return vehicles;
    }

    public void setVehicles(ArrayList<String> vehicles) {
        this.vehicles = vehicles;
    }

    public ArrayList<String> getFacilities() {
        return facilities;
    }

    public void setFacilities(ArrayList<String> facilities) {
        this.facilities = facilities;
    }

    public boolean isWheelchair_accessible() {
        return wheelchair_accessible;
    }

    public void setWheelchair_accessible(boolean wheelchair_accessible) {
        this.wheelchair_accessible = wheelchair_accessible;
    }


    public String formatQuery() {
        // Mandatory arguments
        String departure = this.getDeparture();
        String destination = this.getDestination();
        String viaStation = this.getViaStation();
        ArrayList<String> vehicles = this.getVehicles();
        ArrayList<String> facilities = this.getFacilities();
        boolean wheelchair_accessible = this.isWheelchair_accessible();
        String query = String.format("?fromStation=%s&toStation=%s", departure, destination);

        // Only extend query if argument is given
        query = (viaStation != null) ? String.format("%s&viaStation=%s", query, viaStation) : query;
//        query = (vehicles.isEmpty()) ? String.format("%s&something=%s", query, viaStation) : query; // Cannot find how to select for vehicles
//        query = (facilities.isEmpty()) ? String.format("%s&something=%s", query, viaStation) : query; // Idem for facilities, maybe bathroom->only in trains and silentCompartment->?
//        query = wheelchair_accessible ? String.format("%s&something=%s", query, viaStation) : query; // Found 'searchForAccessible' but is not for wheelchairs (but for people with autism appearently)

        return query;
    }


    // getTrip from departure to destination using the NS trips API
    public List<Trip> getTrip() {
        String query = this.formatQuery();

        List<Trip> trips = null;
        try {
            // Setup connection
            String urlString = scheme + trips_path + query;
            LOGGER.info(urlString);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            //Request headers
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", "719f331db2dc42feb5e8781c34cd5711");


            // Get response from API
            int status = connection.getResponseCode();
            LOGGER.info("Response Code: " + (status));

            // Read response from API
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            // Select relevant part of response
            ObjectMapper mapperB = new ObjectMapper();
            JsonNode rootNode = mapperB.readTree(content.toString());

            // Create trip objects
            trips = nodeToTrips(rootNode);

            connection.disconnect();
        } catch (Exception ex) {
            LOGGER.info("exception:" + ex.getCause());
            ex.printStackTrace();

        }
        return trips;
    }



    public List<Trip> nodeToTrips(JsonNode node) throws ParseException {
        List<Trip> trips = new ArrayList<Trip>();
        for (JsonNode n : node.get("trips")) {
            String uid = n.get("idx").toString();
            int plannedDurationInMinutes = n.get("plannedDurationInMinutes").intValue();
            int transfers = n.get("transfers").intValue();
            String status = n.get("status").toString();

            List<Leg> legs = nodeToLegs(n);
            System.out.println(n.toString());
            String crowdForecast = n.get("crowdForecast").toString();
            //Integer fares = n.get("fares").get("priceInCents").intValue();
            boolean optimal = n.get("optimal").booleanValue();
            Trip t = new Trip(uid, plannedDurationInMinutes, transfers, status, legs, crowdForecast, optimal);

            System.out.println(t);
            trips.add(t);
        }

        return trips;
    }

    public List<Leg> nodeToLegs(JsonNode node) throws ParseException{
        List<Leg> legs = new ArrayList<Leg>();
        for (JsonNode n : node.get("legs")) {
            String idx = n.get("idx").toString();
            String origin = n.get("origin").get("name").toString();
            origin = origin.substring(1,origin.length()-1);
            String destination = n.get("destination").get("name").toString();
            destination = destination.substring(1,destination.length()-1);
            String name = n.get("name").toString();
            String direction = n.get("direction").toString();
            String productNumber = n.get("product").get("number").toString(); // Go into product object
            String productType = n.get("product").get("type").toString(); // Go into product object
            String productDisplayName = n.get("product").get("displayName").toString();

            List<Stop> stops = nodeToStops(n);

            String crowdForecast = n.get("crowdForecast").toString();

            Leg leg = new Leg(idx, origin, destination, name, direction, productNumber, productType, productDisplayName, stops, crowdForecast);
            legs.add(leg);
        }
        return legs;
    }

    public List<Stop> nodeToStops(JsonNode node) throws ParseException {
        List<Stop> stops = new ArrayList<Stop>();
        for (JsonNode n : node.get("stops")) {
            String uicCode = n.get("uicCode").toString();
            String name = n.get("name").toString();
            String plannedArrivalDateTime = (n.get("plannedArrivalDateTime") != null) ? n.get("plannedArrivalDateTime").toString(): "2000-04-03T10:00:00Z"; //Assigning random date to fix null value issue
            String plannedDepartureDateTime = (n.get("plannedDepartureDateTime") != null) ? n.get("plannedDepartureDateTime").toString(): "2000-04-03T10:00:00Z";

            LOGGER.info(plannedArrivalDateTime);
            LOGGER.info(plannedDepartureDateTime);
            Stop stop = new Stop(uicCode, name, plannedArrivalDateTime, plannedDepartureDateTime);
            stops.add(stop);
        }

        return stops;
    }


    public static void main(String[] args) {
        NSAPI app = new NSAPI();
        app.setDeparture("Enschede");
        app.setDestination("Utrecht");
        List<Trip> trips = app.getTrip();
    }
}