package com.example.lb1_javafx.model.user;

import com.example.lb1_javafx.CallEndpoints;
import com.example.lb1_javafx.model.forum.ClassComment;
import com.example.lb1_javafx.model.order.ClassTrip;
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
@Entity(name = "USERS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", nullable = false)
    private Long id;

    @Column(name = "ACCOUNT_TYPE", nullable = false)
    private UserAccountType account_type;

    @Column(name = "FIRST_NAME", nullable = false, length = 20)
    private String first_name;

    @Column(name = "LAST_NAME", nullable = false, length = 20)
    private String last_name;

    @Column(name = "STATUS")
    private UserStatus status;

    @Column(name = "EMAIL", nullable = false, length = 30, unique = true)
    private String email;

    @Column(name = "LOGIN", nullable = false, length = 20, unique = true)
    private String login;

    @Column(name = "PASSWORD", nullable = false, length = 20)
    private String password;

    @Column(name = "PHONE_NUMBER", nullable = false, unique = true)
    private String phone_number;

    @Column(name = "SALARY", nullable = false)
    private String salary;

    @OneToMany(mappedBy = "driver")
    private List<ClassTrip> trips;

    @OneToMany(mappedBy = "user")
    private List<ClassComment> comments;

    @OneToMany(mappedBy = "user")
    private List<ClassComment> forums;

    public ClassUser(JSONObject jsonObject) {
        this.id = Long.valueOf((Integer) jsonObject.get("id"));
        this.account_type = (UserAccountType.valueOf(jsonObject.get("accountType").toString()));
        this.first_name = (String) jsonObject.get("firstName");
        this.last_name = (String) jsonObject.get("lastName");
        this.status = (UserStatus.valueOf(jsonObject.get("status").toString()));
        this.email = (String) jsonObject.get("email");
        this.login = (String) jsonObject.get("login");
        this.password = (String) jsonObject.get("password");
        this.phone_number = (String) jsonObject.get("phoneNumber");
        this.salary = (String) jsonObject.get("salary");
    }

    public static List<ClassUser> getArray() {

        String response = CallEndpoints.Get("http://localhost:8080/api/user/users");
        List<ClassUser> users = new ArrayList<ClassUser>();
        JSONArray responseArray = new JSONArray(response);
        if (responseArray != null) {
            for (int i=0;i<responseArray.length();i++){
                users.add(new ClassUser(responseArray.getJSONObject(i)));
            }
        }
        return users;
    }
}
