package com.example.lb1_javafx.model.order;

import com.example.lb1_javafx.CallEndpoints;
import com.example.lb1_javafx.model.ClassTruck;
import com.example.lb1_javafx.model.user.ClassUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity(name = "StopPoints")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassStopPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STOPPOINT_ID", nullable = false)
    private Long id;

    @Column(name = "STOPPOINT_TIME")
    private LocalDate stopDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHIPMENT_ID", nullable = false)
    private ClassShipment shipment;

    private Long shipment_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRIP_ID", nullable = false)
    private ClassTrip trip;
    private Long trip_id;

    public ClassStopPoint(JSONObject jsonObject) {
        this.id = Long.valueOf((Integer) jsonObject.get("id"));
        if (jsonObject.get("stopDate").toString() == "null") this.stopDate = null;
        else this.stopDate = LocalDate.parse(jsonObject.get("stopDate").toString());
        getShipment(Long.valueOf((Integer) jsonObject.get("shipmentId")));
        getTrip(Long.valueOf((Integer) jsonObject.get("tripId")));
    }
    public static List<ClassStopPoint> getArray() {
        return getArray(CallEndpoints.Get("http://localhost:8080/api/stopPoint/stopPoints"));
    }
    public static List<ClassStopPoint> getArray(String body) {
        List<ClassStopPoint> stopPoints = new ArrayList<ClassStopPoint>();
        JSONArray responseArray = new JSONArray(body);
        if (responseArray != null) {
            for (int i=0;i<responseArray.length();i++){
                stopPoints.add(new ClassStopPoint(responseArray.getJSONObject(i)));
            }
        }
        return stopPoints;
    }

    private void getShipment(Long id)
    {
        this.shipment_id = id;
        String response = CallEndpoints.Get("http://localhost:8080/api/shipment/shipments?id=" + id);
        JSONArray responseArray = new JSONArray(response);
        if (responseArray != null) {
            for (int i = 0; i < responseArray.length(); i++) {
                this.shipment = new ClassShipment(responseArray.getJSONObject(i));
            }
        }
    }
    private void getTrip(Long id)
    {
        this.trip_id = id;
        String response = CallEndpoints.Get("http://localhost:8080/api/trip/trips?id=" + id);
        JSONArray responseArray = new JSONArray(response);
        if (responseArray != null) {
            for (int i = 0; i < responseArray.length(); i++) {
                this.trip = new ClassTrip(responseArray.getJSONObject(i));
            }
        }
    }
}
