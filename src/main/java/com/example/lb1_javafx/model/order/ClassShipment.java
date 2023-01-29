package com.example.lb1_javafx.model.order;

import com.example.lb1_javafx.CallEndpoints;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;
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
    @JoinColumn(name = "DESTINATION_ID")
    private ClassDestination destination;
    private Long destinationId;

    public String destinationInfo;

    @OneToMany(mappedBy = "shipment")
    private List<ClassStopPoint> stopPoints;

    public ClassShipment(JSONObject jsonObject) {
        this.id = Long.valueOf((Integer) jsonObject.get("id"));
        this.description = (String) jsonObject.get("description");
        this.weight = (String) jsonObject.get("weight");
        getDestination(Long.valueOf((Integer) jsonObject.get("destinationId")));
        destinationInfo = this.destination.getCountry() + ", " +
                          this.destination.getCity() + ", " +
                          this.destination.getStreetAddress();
    }

    public static List<ClassShipment> getArray() {
        return getArray(CallEndpoints.Get("http://localhost:8080/api/shipment/shipments"));
    }
    public static List<ClassShipment> getArray(String body) {

        List<ClassShipment> shipments = new ArrayList<ClassShipment>();
        JSONArray responseArray = new JSONArray(body);
        if (responseArray != null) {
            for (int i = 0; i < responseArray.length(); i++) {
                shipments.add(new ClassShipment(responseArray.getJSONObject(i)));
            }
        }
        return shipments;
    }

    private void getDestination(Long id)
    {
        this.destinationId = id;
        String response = CallEndpoints.Get("http://localhost:8080/api/destination/destinations?id=" + id);
        List<ClassDestination> destination = new ArrayList<ClassDestination>();
        JSONArray responseArray = new JSONArray(response);
        if (responseArray != null) {
            for (int i = 0; i < responseArray.length(); i++) {
                this.destination = new ClassDestination(responseArray.getJSONObject(i));
            }
        }
    }
}