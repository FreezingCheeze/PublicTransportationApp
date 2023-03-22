package app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.UnsupportedEncodingException;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.FileInputStream;

public class NSAPI {
    private String scheme = "https://";
    private String trips_path = "gateway.apiportal.ns.nl/reisinformatie-api/api/v3/trips";

    public class Trip {
        private String uid;
        private int plannedDurationInMinutes;
        private int transfers;
        private String status;
        private List<Leg> legs;
        private String crowdForecast;
        private boolean optimal;

        public Trip(String uid, int plannedDurationInMinutes, int transfers, String status, List<Leg> legs, String crowdForecast, boolean optimal) {
            this.uid = uid;
            this.plannedDurationInMinutes = plannedDurationInMinutes;
            this.status = status;
            this.legs = legs;
            this.transfers = transfers;
            this.crowdForecast = crowdForecast;
            this.optimal = optimal;
        }

        @Override
        public String toString() {
            return "\nTrip{" +
                    "\nuid='" + uid + '\'' +
                    ", \nplannedDurationInMinutes=" + plannedDurationInMinutes +
                    ", \ntransfers=" + transfers +
                    ", \nstatus='" + status + '\'' +
                    ", \nlegs=" + legs +
                    ", \ncrowdForecast='" + crowdForecast + '\'' +
                    ", \noptimal=" + optimal +
                    "\n}";
        }
    }

    public class Leg {
        private String idx;
        private String name;
        private String direction;
        private String productNumber;
        private String productType;
        private String productDisplayName;
        private List<Stop> stops;
        private String crowdForecast;

        public Leg(String idx, String name, String direction, String productNumber, String productType, String productDisplayName, List<Stop> stops, String crowdForecast) {
            this.idx = idx;
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
                    ", \n\tdirection='" + direction + '\'' +
                    ", \n\tproductNumber='" + productNumber + '\'' +
                    ", \n\tproductType='" + productType + '\'' +
                    ", \n\tproductDisplayName='" + productDisplayName + '\'' +
                    ", \n\tstops=" + stops +
                    ", \n\tcrowdForecast='" + crowdForecast + '\'' +
                    "\n\t}";
        }
    }

    public class Stop {
        private String uicCode;
        private String name;
        private String plannedArrivalDateTime; // Make LocalDateTime type
        private String plannedDepartureDateTime; // always timezone offset of 60?

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
            String name = n.get("name").toString();
            String direction = n.get("direction").toString();
            String productNumber = n.get("product").get("number").toString(); // Go into product object
            String productType = n.get("product").get("type").toString(); // Go into product object
            String productDisplayName = n.get("product").get("displayName").toString();

            List<Stop> stops = nodeToStops(n);

            String crowdForecast = n.get("crowdForecast").toString();

            Leg leg = new Leg(idx, name, direction, productNumber, productType, productDisplayName, stops, crowdForecast);
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