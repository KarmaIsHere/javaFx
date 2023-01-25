package com.example.lb1_javafx.model.order;

import com.example.lb1_javafx.CallEndpoints;
import com.example.lb1_javafx.model.ClassTruck;
import com.example.lb1_javafx.model.TruckStatus;
import com.example.lb1_javafx.model.user.ClassUser;
import com.example.lb1_javafx.model.user.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    @Column(name = "DEADLINE", nullable = false)
    private LocalDate deadline;
    @Column(name = "STATUS")
    private TripStatus status;
    @Column(name = "TRIP_START")
    private LocalDate start;
    @Column(name = "TRIP_END")
    private LocalDate end;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private ClassUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANAGER_ID")
    private ClassUser manager;
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRUCK_ID")
    private ClassTruck truck;
    private Long truckId;

    @OneToMany(mappedBy = "trip")

    private List<ClassStopPoint> stopPoints;
    public ClassTrip(JSONObject jsonObject) {
        this.id = Long.valueOf((Integer) jsonObject.get("id"));
        this.start = LocalDate.parse(jsonObject.get("start").toString());
        this.end = LocalDate.parse(jsonObject.get("end").toString());
        this.status = TripStatus.valueOf(jsonObject.get("status").toString());
        this.deadline = LocalDate.parse(jsonObject.get("deadline").toString());
        getUser(Long.valueOf((Integer) jsonObject.get("user")));
        getTruck(Long.valueOf((Integer) jsonObject.get("truck")));
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

    private void getUser(Long id)
    {
        this.userId = id;
        String response = CallEndpoints.Get("http://localhost:8080/api/user/users?id=" + id);
        List<ClassUser> user = new ArrayList<ClassUser>();
        JSONArray responseArray = new JSONArray(response);
        if (responseArray != null) {
            for (int i = 0; i < responseArray.length(); i++) {
                this.user = new ClassUser(responseArray.getJSONObject(i));
            }
        }
    }
    private void getTruck(Long id)
    {
        this.truckId = id;
        String response = CallEndpoints.Get("http://localhost:8080/api/truck/trucks?id=" + id);
        List<ClassTruck> truck = new ArrayList<ClassTruck>();
        JSONArray responseArray = new JSONArray(response);
        if (responseArray != null) {
            for (int i = 0; i < responseArray.length(); i++) {
                this.truck = new ClassTruck(responseArray.getJSONObject(i));
            }
        }
    }
}
