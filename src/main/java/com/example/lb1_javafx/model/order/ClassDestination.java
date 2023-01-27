package com.example.lb1_javafx.model.order;

import com.example.lb1_javafx.CallEndpoints;
import com.example.lb1_javafx.model.ClassTruck;
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
@Entity(name = "Destinations")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassDestination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DESTINATION_ID", nullable = false)
    private Long id;

    @Column(name = "DESTINATION_COUNTRY", nullable = false)
    private String country;

    @Column(name = "DESTINATION_CITY", nullable = false)
    private String city;

    @Column(name = "DESTINATION_ADDRESS", nullable = false)
    private String streetAddress;

    @OneToMany(mappedBy = "destination")
    private List<ClassShipment> shipments;

    public ClassDestination(JSONObject jsonObject) {
        this.id = Long.valueOf((Integer) jsonObject.get("id"));
        this.country = (String) jsonObject.get("country");
        this.city = (String) jsonObject.get("city");
        this.streetAddress = (String) jsonObject.get("streetAddress");
    }

    public static List<ClassDestination> getArray() {
        return getArray(CallEndpoints.Get("http://localhost:8080/api/destination/destinations"));
    }
    public static List<ClassDestination> getArray(String body) {

        List<ClassDestination> destinations = new ArrayList<ClassDestination>();
        JSONArray responseArray = new JSONArray(body);
        if (responseArray != null) {
            for (int i = 0; i < responseArray.length(); i++) {
                destinations.add(new ClassDestination(responseArray.getJSONObject(i)));
            }
        }
        return destinations;
    }
}
