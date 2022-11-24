package com.example.lb1_javafx.model.order;

import com.example.lb1_javafx.CallEndpoints;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "Shipments")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassShipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHIPMENT_ID", nullable = false)
    private Long id;

    @Column(name = "SHIPMENT_DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "SHIPMENT_WEIGHT", nullable = false)
    private String weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DESTINATION_ID", nullable = false)
    private ClassDestination destination;

    @OneToMany(mappedBy = "shipment")
    private List<ClassStopPoint> stopPoints;

    public ClassShipment(JSONObject jsonObject) {
        this.id = Long.valueOf((Integer) jsonObject.get("id"));
        this.description = (String) jsonObject.get("description");
        this.weight = (String) jsonObject.get("weight");
        this.destination = (ClassDestination) jsonObject.get("destination");
    }

    public static List<ClassShipment> getArray() {

        String response = CallEndpoints.Get("http://localhost:8080/api/shipment/shipments");
        List<ClassShipment> shipments = new ArrayList<ClassShipment>();
        JSONArray responseArray = new JSONArray(response);
        if (responseArray != null) {
            for (int i = 0; i < responseArray.length(); i++) {
                shipments.add(new ClassShipment(responseArray.getJSONObject(i)));
            }
        }
        return shipments;
    }
}