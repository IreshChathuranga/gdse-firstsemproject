package lk.ijse.gdse.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.finalproject.dto.CoursesDto;
import lk.ijse.gdse.finalproject.dto.tm.CoursesTM;
import lk.ijse.gdse.finalproject.model.CoursesModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CoursesController implements Initializable {
    public TableView<CoursesTM> tblCourse;
    public TableColumn<CoursesTM,String> courseId;
    public TableColumn<CoursesTM,String> courseName;
    public TableColumn<CoursesTM,String> adminId;
    public Label lblCourseId;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public TextField txtAdmin;
    public TextField txtCourseName;
    CoursesModel coursesModel = new CoursesModel();
    public void loadTableData() throws SQLException, ClassNotFoundException{
        ArrayList<CoursesDto> coursesDtos = coursesModel.getAllCourse();
        ObservableList<CoursesTM> coursesTMS = FXCollections.observableArrayList();
        for(CoursesDto coursesDto:coursesDtos){
            CoursesTM coursesTM = new CoursesTM();
            coursesTM.setCourseId(coursesDto.getCourseId());
            coursesTM.setCourseName(coursesDto.getCourseName());
            coursesTM.setAdminId(coursesDto.getAdminId());
            coursesTMS.add(coursesTM);
        }
        tblCourse.setItems(coursesTMS);
    }
    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextCourseId();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtCourseName.setText("");
        txtAdmin.setText("");
    }
    public void loadNextCourseId() throws SQLException, ClassNotFoundException {
        String nextCourseId = coursesModel.getNextCourseId();
        lblCourseId.setText(nextCourseId);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        courseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        adminId.setCellValueFactory(new PropertyValueFactory<>("adminId"));

        try{
            refreshPage();
        }catch(Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail Course id").show();
        }

    }

    public void refreshOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    public void onClickTable(MouseEvent mouseEvent) {
        CoursesTM coursesTM = tblCourse.getSelectionModel().getSelectedItem();
        if (coursesTM != null) {
            lblCourseId.setText(coursesTM.getCourseId());
            txtCourseName.setText(coursesTM.getCourseName());
            txtAdmin.setText(coursesTM.getAdminId());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String courseId = lblCourseId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = coursesModel.deleteCourse(courseId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Course deleted").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete course...!").show();
            }
        }
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String courseId = lblCourseId.getText();
        String courseName = txtCourseName.getText();
        String adminId = txtAdmin.getText();

        CoursesDto coursesDto = new CoursesDto(
                courseId,
                courseName,
                adminId
        );
        boolean isUpdated = coursesModel.updateCourse(coursesDto);
        if (isUpdated) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Course Updated").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Update fail").show();

        }
    }

    public void saveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String courseId = lblCourseId.getText();
        String courseName = txtCourseName.getText();
        String adminId = txtAdmin.getText();

        CoursesDto coursesDto = new CoursesDto(
                courseId,
                courseName,
                adminId
        );
        boolean isSaved = coursesModel.saveCourse(coursesDto);
        if (isSaved) {
            loadNextCourseId();
            txtCourseName.setText("");
            txtAdmin.setText("");
            new Alert(Alert.AlertType.INFORMATION, "Course Saved").show();
            loadTableData();
        } else {
            new Alert(Alert.AlertType.ERROR, "Save fail").show();

        }
    }
}
