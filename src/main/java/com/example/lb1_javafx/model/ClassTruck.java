package com.example.lb1_javafx.model;

import com.example.lb1_javafx.CallEndpoints;
import com.example.lb1_javafx.model.order.ClassTrip;
import com.example.lb1_javafx.model.user.ClassUser;
import com.example.lb1_javafx.model.user.UserAccountType;
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
@Entity(name = "Trucks")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassTruck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRUCK_ID", nullable = false)
    private Long id;

    @Column(name = "TRUCK_BRAND", nullable = false)
    private String brand;

    @Column(name = "TRUCK_YEAR", nullable = false)
    private String year;

    @Column(name = "TRUCK_HEIGHT", nullable = false)
    private String height;

    @Column(name = "TRUCK_WEIGHT", nullable = false)
    private String weight;

    @Column(name = "STATUS", nullable = false)
    private TruckStatus status;

    @OneToMany(mappedBy = "truck")
    private List<ClassTrip> trips;

    public ClassTruck(JSONObject jsonObject) {
        this.id = Long.valueOf((Integer) jsonObject.get("id"));
        this.brand = (String) jsonObject.get("brand");
        this.year = (String) jsonObject.get("year");
        this.height = (String) jsonObject.get("height");
        this.weight = (String) jsonObject.get("weight");
        this.status = (TruckStatus.valueOf(jsonObject.get("status").toString()));
    }
    public static List<ClassTruck> getArray() {

        String response = CallEndpoints.Get("http://localhost:8080/api/truck/trucks");

        List<ClassTruck> trucks = new ArrayList<ClassTruck>();
        JSONArray responseArray = new JSONArray(response);
   
        if (responseArray != null) {
            for (int i=0;i<responseArray.length();i++){
                trucks.add(new ClassTruck(responseArray.getJSONObject(i)));
            }
        }
        return trucks;
    }
}
