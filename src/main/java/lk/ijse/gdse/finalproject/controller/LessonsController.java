package lk.ijse.gdse.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.finalproject.dto.LessonsDto;
import lk.ijse.gdse.finalproject.dto.tm.LessonsTM;
import lk.ijse.gdse.finalproject.model.LessonsModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class LessonsController implements Initializable {
    public TableView<LessonsTM> tblLesson;
    public TableColumn<LessonsTM,String> lessonName;
    public TableColumn<LessonsTM,String> timePeriod;
    public TableColumn<LessonsTM,String> studentId;
    public TableColumn<LessonsTM,String> instructorId;
    public TextField txtLessonName;
    public TextField txtTimePeriod;
    public TextField txtStudentId;
    public Button btnSave;

    public Button btnDelete;
    public TextField txtInstructorId;
    public Button btnUpdate;
    LessonsModel lessonsModel = new LessonsModel();
    public void loadTableData() throws SQLException, ClassNotFoundException{
        ArrayList<LessonsDto>  lessonsDtos = lessonsModel.getAllLessons();
        ObservableList<LessonsTM> lessonsTMS = FXCollections.observableArrayList();
        for(LessonsDto lessonsDto:lessonsDtos){
            LessonsTM lessonsTM = new LessonsTM();
            lessonsTM.setLessonName(lessonsDto.getLessonName());
            lessonsTM.setTimePeriod(lessonsDto.getTimePeriod());
            lessonsTM.setStudentId(lessonsDto.getStudentId());
            lessonsTM.setInstructorId(lessonsDto.getInstructorId());
            lessonsTMS.add(lessonsTM);
        }
        tblLesson.setItems(lessonsTMS);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lessonName.setCellValueFactory(new PropertyValueFactory<>("lessonName"));
        timePeriod.setCellValueFactory(new PropertyValueFactory<>("timePeriod"));
        studentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        instructorId.setCellValueFactory(new PropertyValueFactory<>("instructorId"));

        try{
            refreshPage();
        }catch(Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail Lesson name").show();
        }
    }

    public void saveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String lessonName = txtLessonName.getText();
        String timePeriod = txtTimePeriod.getText();
        String studentId = txtStudentId.getText();
        String instructorId = txtInstructorId.getText();

        LessonsDto lessonsDto = new LessonsDto(
                lessonName,
                timePeriod,
                studentId,
                instructorId
        );
        boolean isSaved = lessonsModel.saveLesson(lessonsDto);
        if (isSaved) {
            txtLessonName.setText("");
            txtTimePeriod.setText("");
            txtStudentId.setText("");
            txtInstructorId.setText("");
            new Alert(Alert.AlertType.INFORMATION, "Lesson Saved").show();
            loadTableData();
        } else {
            new Alert(Alert.AlertType.ERROR, "Save fail").show();

        }
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String lessonName = txtLessonName.getText();
        String timePeriod = txtTimePeriod.getText();
        String studentId = txtStudentId.getText();
        String instructorId = txtInstructorId.getText();

        LessonsDto lessonsDto = new LessonsDto(
                lessonName,
                timePeriod,
                studentId,
                instructorId
        );
        boolean isUpdated = lessonsModel.updateLesson(lessonsDto);
        if (isUpdated) {
           refreshPage();
           new Alert(Alert.AlertType.INFORMATION, "Lesson Updated").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Update fail").show();

        }
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String lessonName = txtLessonName.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = lessonsModel.deleteLesson(lessonName);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Lesson deleted").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete lesson...!").show();
            }
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        LessonsTM lessonsTM = tblLesson.getSelectionModel().getSelectedItem();
        if (lessonsTM != null) {
            txtLessonName.setText(lessonsTM.getLessonName());
            txtTimePeriod.setText(lessonsTM.getTimePeriod());
            txtStudentId.setText(lessonsTM.getStudentId());
            txtInstructorId.setText(lessonsTM.getInstructorId());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtTimePeriod.setText("");
        txtLessonName.setText("");
        txtStudentId.setText("");
        txtInstructorId.setText("");
    }
    public void refreshOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        refreshPage();
    }
}
