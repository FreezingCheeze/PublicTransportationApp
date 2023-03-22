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
        private Leg[] legs;
        private String crowdForecast;
        private boolean optimal;

        public Trip(String uid, int plannedDurationInMinutes, int transfers, String status, Leg[] legs, String crowdForecast, boolean optimal) {
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
            return "Trip{" +
                    "\nuid='" + uid + '\'' +
                    ", \nplannedDurationInMinutes=" + plannedDurationInMinutes +
                    ", \ntransfers=" + transfers +
                    ", \nstatus='" + status + '\'' +
                    ", \ncrowdForecast='" + crowdForecast + '\'' +
                    ", \noptimal=" + optimal +
                    '}';
        }
    }

    public class Leg {
        private String idx;
        private String name;
        private String direction;
        private String productNumber;
        private String productType;
        private String productDisplayName;
        private Stop[] stops;
        private String crowdForecast;
    }

    public class Stop {
        private String uicCode;
        private String name;
        private LocalDateTime plannedArrivalDateTime;
        private LocalDateTime plannedDepartureDateTime; // always timezone offset of 60?
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
            System.out.println(content);

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
            Leg[] legs = new Leg[0];  // = n.get("Legs");
            String crowdForecast = n.get("crowdForecast").toString();
            boolean optimal = n.get("optimal").booleanValue();
            Trip t = new Trip(uid, plannedDurationInMinutes, transfers, status, legs, crowdForecast, optimal);

            trips.add(t);
        }

        return trips;
    }


    public static void main(String[] args) {
        NSAPI app = new NSAPI();
        app.getTrip("Enschede", "Utrecht");
    }
}