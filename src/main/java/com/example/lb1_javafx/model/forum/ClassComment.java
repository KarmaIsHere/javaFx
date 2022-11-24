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
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "Comments")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID", nullable = false)
    private Long id;

    @Column(name = "COMMENT_CONTENT", nullable = false)
    private String text;

    @Column(name = "COMMENT_DATE", nullable = false)
    private Instant date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FORUM_ID", nullable = false)
    private ClassForum forum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private ClassUser user;

    public ClassComment(JSONObject jsonObject) {
        this.id = Long.valueOf((Integer) jsonObject.get("id"));
        this.text = (String) jsonObject.get("text");
        this.date = (Instant) jsonObject.get("date");
        this.forum = (ClassForum) jsonObject.get("forum");
        this.user = (ClassUser) jsonObject.get("user");


    }

    public static List<ClassComment> getArray() {

        String response = CallEndpoints.Get("http://localhost:8080/api/comment/comments");
        List<ClassComment> comments = new ArrayList<ClassComment>();
        JSONArray responseArray = new JSONArray(response);
        if (responseArray != null) {
            for (int i = 0; i < responseArray.length(); i++) {
                comments.add(new ClassComment(responseArray.getJSONObject(i)));
            }
        }
        return comments;
    }
}
