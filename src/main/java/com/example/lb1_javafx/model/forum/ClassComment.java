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
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FORUM_ID", nullable = false)
    private ClassForum forum;
    private Long forumId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private ClassUser user;
    private Long userId;

    private String userInfo;

    public ClassComment(JSONObject jsonObject) {
        this.id = Long.valueOf((Integer) jsonObject.get("id"));
        this.text = (String) jsonObject.get("text");
        this.date = LocalDate.parse(jsonObject.get("date").toString());
        getForum( Long.valueOf((Integer) jsonObject.get("forum")));
        getUser( Long.valueOf((Integer) jsonObject.get("user")));
        this.userInfo = this.user.getFirst_name() + " " + this.user.getLast_name() + " (" + this.user.getEmail() + ") ";
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

    public static List<ClassComment> getForumArray(Long id) {

        String response = CallEndpoints.Get("http://localhost:8080/api/comment/comments?forumId=" + id);
        List<ClassComment> comments = new ArrayList<ClassComment>();
        JSONArray responseArray = new JSONArray(response);
        if (responseArray != null) {
            for (int i = 0; i < responseArray.length(); i++) {
                comments.add(new ClassComment(responseArray.getJSONObject(i)));
            }
        }
        return comments;
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
    private void getForum(Long id)
    {
        this.forumId = id;
        String response = CallEndpoints.Get("http://localhost:8080/api/forum/forums?id=" + id);
        List<ClassForum> forum = new ArrayList<ClassForum>();
        JSONArray responseArray = new JSONArray(response);
        if (responseArray != null) {
            for (int i = 0; i < responseArray.length(); i++) {
                this.forum = new ClassForum(responseArray.getJSONObject(i));
            }
        }
    }
}
