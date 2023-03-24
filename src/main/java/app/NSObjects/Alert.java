package app.NSObjects;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// Sends alerts to queue, which client can read.
public class Alert {

    // Arrivals API needs codes for the stations
    private ArrayList<String> important_stations = new ArrayList<String>() {
        {   // Amsterdam - Utrecht - Enschede
            add("ASD");
            add("ASA");
            add("UT");
            add("AMF");
            add("APD");
            add("DV");
            add("AML");
            add("HGL");
            add("ES");
        }
    };

    private String arrivals_path = "https://gateway.apiportal.ns.nl/reisinformatie-api/api/v2/arrivals";


    public List<Arrival> getArrivals(String destination_code) {
        List<Arrival> arrivals = null;

        try {
            // Setup connection
            String urlString = String.format("%s?station=%s", arrivals_path, destination_code);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Request headers
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", "719f331db2dc42feb5e8781c34cd5711");
            connection.setRequestMethod("GET");

            // Read response from API
            int status = connection.getResponseCode();
            System.out.println(status);

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
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(content.toString());

            // Create trip objects
            arrivals = nodeToArrivals(rootNode);

            connection.disconnect();

        } catch (Exception ex) {
            ex.printStackTrace();
        }


            return arrivals;
    }

    public List<Arrival> nodeToArrivals(JsonNode node) throws ParseException {
        List<Arrival> arrivals = new ArrayList<Arrival>();
        JsonNode payload = node.get("payload");
        for (JsonNode n : payload.get("arrivals")) {
            String origin = n.get("origin").toString();
            String name = n.get("name").toString();
            String plannedDateTime = (n.get("plannedDateTime") != null) ? n.get("plannedDateTime").toString(): "2000-04-03T10:00:00Z";
            String plannedTrack = n.get("plannedTrack").toString();

            Arrival a = new Arrival(origin, name, plannedDateTime, plannedTrack);

            System.out.println(a);
            arrivals.add(a);
        }

        return arrivals;
    }

    public static void main(String[] args) {
        Alert alert = new Alert();
        alert.getArrivals("ES");
    }
}
