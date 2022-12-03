package com.example.lb1_javafx.model.order;

import com.example.lb1_javafx.CallEndpoints;
import com.example.lb1_javafx.model.ClassTruck;
import com.example.lb1_javafx.model.TruckStatus;
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
@Entity(name = "Trips")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassTrip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRIP_ID", nullable = false)
    private Long id;

    @Column(name = "TRIP_START")
    private LocalDate start;

    @Column(name = "TRIP_END")
    private LocalDate end;

    @Column(name = "DEADLINE", nullable = false)
    private LocalDate deadline;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private ClassUser driver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRUCK_ID")
    private ClassTruck truck;

    @OneToMany(mappedBy = "trip")

    private List<ClassStopPoint> stopPoints;
    public ClassTrip(JSONObject jsonObject) {
        this.id = Long.valueOf((Integer) jsonObject.get("id"));
        this.start = (LocalDate) jsonObject.get("start");
        this.end = (LocalDate) jsonObject.get("end");
        this.driver = (ClassUser) jsonObject.get("driver");
        this.truck = (ClassTruck) jsonObject.get("truck");
    }
    public static List<ClassTrip> getArray() {

        String response = CallEndpoints.Get("http://localhost:8080/api/trip/trips");

        List<ClassTrip> trips = new ArrayList<ClassTrip>();
        JSONArray responseArray = new JSONArray(response);
   
        if (responseArray != null) {
            for (int i=0;i<responseArray.length();i++){
                trips.add(new ClassTrip(responseArray.getJSONObject(i)));
            }
        }
        return trips;
    }

}
