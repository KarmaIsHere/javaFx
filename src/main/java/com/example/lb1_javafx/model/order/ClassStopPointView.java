package com.example.lb1_javafx.model.order;

import com.example.lb1_javafx.CallEndpoints;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.lb1_javafx.utils.TrimResponse.trimJSON;

@Data
@Builder
public class ClassStopPointView {


    private final Long shipment_id;
    private final String description;
    private final String weight;
    private final String country;
    private final String city;
    private final String streetAddress;

    public ClassStopPointView(JSONObject jsonObject) {
        this.shipment_id = Long.valueOf((Integer) jsonObject.get("shipment_id"));
        this.description = (String) jsonObject.get("description");
        this.weight = (String) jsonObject.get("weight");
        this.country = (String) jsonObject.get("country");
        this.city = (String) jsonObject.get("city");
        this.streetAddress = (String) jsonObject.get("streetAddress");
    }

    public ClassStopPointView(Long shipment_id, String description, String weight, String country, String city, String streetAddress) {
        this.shipment_id = shipment_id;
        this.description = description;
        this.weight = weight;
        this.country = country;
        this.city = city;
        this.streetAddress = streetAddress;
    }

    public static List<ClassStopPointView> getArray(Long trip_id) {

        List<ClassStopPointView> stopPoints = new ArrayList<ClassStopPointView>();

        String stopPointsBody =  CallEndpoints.Get("http://localhost:8080/api/stopPoint/stopPoints?trip_id=" + trip_id);

        JSONArray stopPointsArray = new JSONArray(stopPointsBody);

        for (int i=0;i<stopPointsArray.length();i++){

            String response = CallEndpoints.Get("http://localhost:8080/api/shipment/shipments?id=" + stopPointsArray.getJSONObject(i).get("shipmentId"));

            response = trimJSON(response);

            String shipmentBody = response;

            JSONObject shipmentJson = new JSONObject(shipmentBody);

            response = null;

            response = CallEndpoints.Get("http://localhost:8080/api/destination/destinations?id=" + shipmentJson.get("destinationId"));

            response = trimJSON(response);

            String destinationBody = response;
            JSONObject destinationJson = new JSONObject(destinationBody);

            stopPoints.add(new ClassStopPointView(stopPointsArray.getJSONObject(i).getLong("shipmentId"),
                    shipmentJson.get("description").toString(),
                    shipmentJson.get("weight").toString(),
                    destinationJson.get("country").toString(),
                    destinationJson.get("city").toString(),
                    destinationJson.get("streetAddress").toString()
                    ));
        }
        return stopPoints;
    }
}
