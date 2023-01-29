package com.example.lb1_javafx.model.forum;

import com.example.lb1_javafx.CallEndpoints;
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
@Entity(name = "Forums")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassForum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FORUM_ID", nullable = false)
    private Long id;

    @Column(name = "FORUM_TITLE", nullable = false)
    private String title;

    @Column(name = "FORUM_DESCRIPTION")
    private String description;

    @Column(name = "FORUM_CATEGORY")
    private String category;

    @OneToMany(mappedBy = "forum")
    private List<ClassComment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private ClassUser user;
    private Long userId;

    @Column(name = "FORUM_CREATIONDATE", nullable = false)
    private LocalDate date;

    public ClassForum(JSONObject jsonObject) {
        this.id = Long.valueOf((Integer) jsonObject.get("id"));
        this.title = (String) jsonObject.get("title");
        this.description = (String) jsonObject.get("description");
        this.category = (String) jsonObject.get("category");
        this.date =  LocalDate.parse(jsonObject.get("date").toString());
        getUser( Long.valueOf((Integer) jsonObject.get("user")));


    }

    public static List<ClassForum> getArray() {

        String response = CallEndpoints.Get("http://localhost:8080/api/forum/forums");
        List<ClassForum> forums = new ArrayList<ClassForum>();
        JSONArray responseArray = new JSONArray(response);
        if (responseArray != null) {
            for (int i = 0; i < responseArray.length(); i++) {
                forums.add(new ClassForum(responseArray.getJSONObject(i)));
            }
        }
        return forums;
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
}


