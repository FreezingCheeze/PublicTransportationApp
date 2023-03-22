package app;

import app.NSObjects.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NSAPI {
    private String scheme = "https://";
    private String trips_path = "gateway.apiportal.ns.nl/reisinformatie-api/api/v3/trips";

    // getTrip from departure to destination using the NS trips API
    public void getTrip(String departure, String destination) {
        try {
            // Create query
            String query = String.format("?fromStation=%s&toStation=%s", departure, destination);

            // Setup connection
            String urlString = scheme + trips_path + query;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Request headers
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", "719f331db2dc42feb5e8781c34cd5711");
            connection.setRequestMethod("GET");

            // Get response from API
            int status = connection.getResponseCode();
            System.out.println(status);

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
            List<Trip> trips = nodeToTrips(rootNode);



            connection.disconnect();
        } catch (Exception ex) {
            System.out.print("exception:" + ex.getMessage());
        }
    }

    public List<Trip> nodeToTrips(JsonNode node) {
        List<Trip> trips = new ArrayList<Trip>();
        for (JsonNode n : node.get("trips")) {
            String uid = n.get("idx").toString();
            int plannedDurationInMinutes = n.get("plannedDurationInMinutes").intValue();
            int transfers = n.get("transfers").intValue();
            String status = n.get("status").toString();

            List<Leg> legs = nodeToLegs(n);

            String crowdForecast = n.get("crowdForecast").toString();
            boolean optimal = n.get("optimal").booleanValue();
            Trip t = new Trip(uid, plannedDurationInMinutes, transfers, status, legs, crowdForecast, optimal);

            System.out.println(t);
            trips.add(t);
        }

        return trips;
    }

    public List<Leg> nodeToLegs(JsonNode node) {
        List<Leg> legs = new ArrayList<Leg>();
        for (JsonNode n : node.get("legs")) {
            String idx = n.get("idx").toString();
            String origin = n.get("origin").get("name").toString();
            String destination = n.get("destination").get("name").toString();
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

    public List<Stop> nodeToStops(JsonNode node) {
        List<Stop> stops = new ArrayList<Stop>();
        for (JsonNode n : node.get("stops")) {
            String uicCode = n.get("uicCode").toString();
            String name = n.get("name").toString();
            String plannedArrivalDateTime = (n.get("plannedArrivalDateTime") != null) ? n.get("plannedArrivalDateTime").toString() : null;
            String plannedDepartureDateTime = (n.get("plannedDepartureDateTime") != null) ? n.get("plannedDepartureDateTime").toString() : null;
            Stop stop = new Stop(uicCode, name, plannedArrivalDateTime, plannedDepartureDateTime);
            stops.add(stop);
        }

        return stops;
    }


    public static void main(String[] args) {
        NSAPI app = new NSAPI();
        app.getTrip("Enschede", "Utrecht");
    }
}