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
    private Long userId;

    private String userInfo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANAGER_ID")
    private ClassUser manager;

    private String managerInfo;
    private Long managerId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRUCK_ID")
    private ClassTruck truck;
    private String truckInfo;
    private Long truckId;

    @OneToMany(mappedBy = "trip")

    private List<ClassStopPoint> stopPoints;
    public ClassTrip(JSONObject jsonObject) {
        this.id = Long.valueOf((Integer) jsonObject.get("id"));
        if (jsonObject.get("start").toString() == "null") this.start = null;
        else this.start = LocalDate.parse(jsonObject.get("start").toString());
        if (jsonObject.get("end").toString() == "null") this.end = null;
        else this.end = LocalDate.parse(jsonObject.get("end").toString());
        this.status = TripStatus.valueOf(jsonObject.get("status").toString());
        this.deadline = LocalDate.parse(jsonObject.get("deadline").toString());
        getUser(Long.valueOf((Integer) jsonObject.get("user")));
        this.userInfo = this.user.getFirst_name() + " " +
                        this.user.getLast_name() + " " +
                        this.user.getPhone_number();
        getTruck(Long.valueOf((Integer) jsonObject.get("truck")));
        this.truckInfo = this.truck.getBrand() + " " +
                         this.truck.getYear() + " (" +
                         this.truck.getId() + ")";
        getManager(Long.valueOf((Integer) jsonObject.get("manager")));
        this.managerInfo = this.manager.getFirst_name() + " " +
                        this.manager.getLast_name() + " " +
                        this.manager.getPhone_number();
    }


    public static List<ClassTrip> getArray() {
        return getArray(CallEndpoints.Get("http://localhost:8080/api/trip/trips"));
    }
    public static List<ClassTrip> getArray(String body) {

        List<ClassTrip> trips = new ArrayList<ClassTrip>();
        JSONArray responseArray = new JSONArray(body);
   
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
        JSONArray responseArray = new JSONArray(response);
        if (responseArray != null) {
            for (int i = 0; i < responseArray.length(); i++) {
                this.truck = new ClassTruck(responseArray.getJSONObject(i));
            }
        }
    }
    private void getManager(Long id)
    {
        this.managerId = id;
        String response = CallEndpoints.Get("http://localhost:8080/api/user/users?id=" + id);
        JSONArray responseArray = new JSONArray(response);
        if (responseArray != null) {
            for (int i = 0; i < responseArray.length(); i++) {
                this.manager = new ClassUser(responseArray.getJSONObject(i));
            }
        }
    }
}
