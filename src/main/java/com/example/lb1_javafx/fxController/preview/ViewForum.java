package com.example.lb1_javafx.fxController.preview;

import com.example.lb1_javafx.CallEndpoints;
import com.example.lb1_javafx.model.forum.ClassComment;
import com.example.lb1_javafx.model.forum.ClassForum;
import com.example.lb1_javafx.utils.FxUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static com.example.lb1_javafx.utils.SceneSwitcher.switchScene;
import static com.example.lb1_javafx.utils.TrimResponse.trimJSON;

public class ViewForum implements Initializable {
    public Button createButton;
    public Button searchButton;
    public Button refresh;
    public Button backButton;
    public Button editButton;
    @FXML
    public TableView<ClassForum> tableForum;
    @FXML
    public TableColumn<ClassForum, Long> id;
    @FXML
    public TableColumn<ClassForum, String> title;
    @FXML
    public TableColumn<ClassForum, Long> userId;
    @FXML
    public TableColumn<ClassForum, LocalDate> date;

    @FXML
    public TableView<ClassComment> tableComment;
    @FXML
    public TableColumn<ClassComment, String> commentText;
    @FXML
    public TableColumn<ClassComment, LocalDate> commentDate;
    @FXML
    public TableColumn<ClassComment, String> commentUserId;


    public TextField titleField;
    public TextField editIdField;
    public TextField idField;
    public Button deleteButton;
    public TextArea viewDescriptionField;
    public TextField viewCreatorField;
    public TextField viewDateField;
    public TextArea descriptionField;


    public TextField forumIdField;
    public Long currentForumId;
    public TextArea commentField;

    FxUtils fxUtils = new FxUtils();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillForumTable();
    }

    public void createEntry(ActionEvent actionEvent) {
        JSONObject json = new JSONObject();


        if (FxUtils.isFieldEmpty(titleField) ||
                FxUtils.isFieldAreaEmpty(descriptionField)) {
            FxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Empty fields",
                    "Cant create an entry with empty fields");
            return;
        }
        json.put("title", titleField.getText());
        json.put("description", descriptionField.getText());
        json.put("category", "aaa");
        json.put("user", 1);
        CallEndpoints.Post("http://localhost:8080/api/forum/create", json.toString());
        System.out.println(json);
        fillForumTable();
    }

    public void searchTable(ActionEvent actionEvent) {
    }

    public void fillForumTable() {
        id.setCellValueFactory(new PropertyValueFactory<ClassForum, Long>("id"));
        title.setCellValueFactory(new PropertyValueFactory<ClassForum, String>("title"));
        tableForum.getItems().setAll(ClassForum.getArray());
    }

    public void fillCommentTable(Long input) {
        commentText.setCellValueFactory(new PropertyValueFactory<ClassComment, String>("text"));
        commentDate.setCellValueFactory(new PropertyValueFactory<ClassComment, LocalDate>("date"));
        commentUserId.setCellValueFactory(new PropertyValueFactory<ClassComment, String>("userInfo"));
        tableComment.getItems().setAll(ClassComment.getForumArray(input));
    }

    public void fieldSelect(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            TablePosition pos = tableForum.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();
            ClassForum item = tableForum.getItems().get(row);
            TableColumn<ClassForum, ?> column0 = tableForum.getColumns().get(0);
            Object column0Value = column0.getCellData(item);
            this.currentForumId = Long.valueOf(column0Value.toString());
            fillCommentTable(currentForumId);
            fillForumDetails(currentForumId);
        }
    }

    public void fillForumDetails(Long forumId) {
        JSONObject forum = new JSONObject(trimJSON(CallEndpoints.Get("http://localhost:8080/api/forum/forums?id=" + forumId)));
        forumIdField.setText(forum.get("id").toString());
        JSONObject creator = new JSONObject(trimJSON(CallEndpoints.Get("http://localhost:8080/api/user/users?id=" + forum.get("id"))));
        viewCreatorField.setText(creator.get("firstName").toString() + " " +
                creator.get("lastName").toString() + " (" +
                creator.get("email").toString() + ")");
        viewDateField.setText(forum.get("date").toString());
        viewDescriptionField.setText(forum.get("description").toString());
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        switchScene("main-window.fxml", createButton);
    }

    public void openEdit(ActionEvent actionEvent) {
    }

    public void submitEdit(ActionEvent actionEvent) {
    }

    public void deleteEntry(ActionEvent actionEvent) {
    }

    public void postComment(ActionEvent actionEvent) {
        JSONObject json = new JSONObject();

        if (FxUtils.isFieldAreaEmpty(commentField)) {
            FxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Empty field",
                    "Cant create an entry with empty field");
            return;
        }
        json.put("text", commentField.getText());
        json.put("user", 1);
        json.put("forum", currentForumId);
        CallEndpoints.Post("http://localhost:8080/api/comment/create", json.toString());
        System.out.println(json);
        fillCommentTable(currentForumId);
    }
}
