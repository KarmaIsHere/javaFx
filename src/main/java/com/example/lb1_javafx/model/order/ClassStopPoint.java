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

    @Column(name = "STOPPOINT_NR", nullable = false)
    private int nr;

    @Column(name = "STOPPOINT_TIME")
    private LocalDate stopDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHIPMENT_ID", nullable = false)
    private ClassShipment shipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRIP_ID", nullable = false)
    private ClassTrip trip;

    public ClassStopPoint(JSONObject jsonObject) {
        this.id = Long.valueOf((Integer) jsonObject.get("id"));
        this.nr = (Integer) jsonObject.get("nr");
        this.stopDate = (LocalDate) jsonObject.get("stopDate");
        this.shipment = (ClassShipment) jsonObject.get("shipment");
        this.trip = (ClassTrip) jsonObject.get("trip");
    }
    public static List<ClassStopPoint> getArray() {

        String response = CallEndpoints.Get("http://localhost:8080/api/stopPoint/stopPoints");
        List<ClassStopPoint> stopPoints = new ArrayList<ClassStopPoint>();
        JSONArray responseArray = new JSONArray(response);
        if (responseArray != null) {
            for (int i=0;i<responseArray.length();i++){
                stopPoints.add(new ClassStopPoint(responseArray.getJSONObject(i)));
            }
        }
        return stopPoints;
    }
}
