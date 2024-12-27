package lk.ijse.gdse.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.finalproject.dto.*;
import lk.ijse.gdse.finalproject.dto.tm.CartTM;
import lk.ijse.gdse.finalproject.model.BookingModel;
import lk.ijse.gdse.finalproject.model.InstructorsModel;
import lk.ijse.gdse.finalproject.model.LessonsModel;
import lk.ijse.gdse.finalproject.model.StudentsModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BookingController implements Initializable {
    public TextField txtDate;
    public TextField txtTime;
    public Button btnReschedule;

    public Button btnDelete;
    public TextField txtReason;
    public TableView<CartTM> tblBook;
    public TableColumn<CartTM, String> bookId;
    public TableColumn<CartTM, Date> bookDate;
    public TableColumn<CartTM, String> bookTime;

    public Label lblBokk;
    public Button btnRefresh;
    public ComboBox<String> cmbStudentId;
    public Label lblStuName;
    public ComboBox<String> cmbInstructor;
    public Label lblinstuName;

    private final StudentsModel studentsModel = new StudentsModel();
    private final InstructorsModel instructorsModel = new InstructorsModel();
    private final BookingModel bookingModel = new BookingModel();
    private final LessonsModel lessonsModel = new LessonsModel();

    private final ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();
    public TableColumn<CartTM, String> studentId;
    public TableColumn<CartTM, String> instructorId;
    public TableColumn<CartTM, String> rescheduleReason;
    public Button btnAddTable;
    public ComboBox<String> cmbLessons;
    public TextField txtTimePeriod;
    public TableColumn<CartTM, String> lessonName;
    public TableColumn<CartTM, String> timePeriod;
    public TableColumn<?, ?> action;
    public Button btnPlaceBooking;
    public void studentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String selectedStudentId = cmbStudentId.getSelectionModel().getSelectedItem();
        StudentsDto studentsDto = studentsModel.findById(selectedStudentId);

        if (studentsDto != null) {
            lblStuName.setText(studentsDto.getStudentName());
        }
    }

    public void refreshOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    public void instructorOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String selectedInstructorId = cmbInstructor.getSelectionModel().getSelectedItem();
        InstructorsDto instructorsDto = instructorsModel.findById(selectedInstructorId);

        if (instructorsDto != null) {
            lblinstuName.setText(instructorsDto.getInstructorName());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();
        try {
            refreshPage();
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load data").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        lblBokk.setText(bookingModel.getNextBookingId());
        loadStudentIds();
        loadInstructorIds();
        loadLessons();

        txtDate.setText("");
        txtTime.setText("");
        cmbStudentId.getSelectionModel().clearSelection();
        cmbInstructor.getSelectionModel().clearSelection();
        lblStuName.setText("");
        lblinstuName.setText("");
        cmbLessons.getSelectionModel().clearSelection();
        txtTimePeriod.setText("");
        txtReason.setText("");

        cartTMS.clear();

        tblBook.refresh();
    }

    private void setCellValues() {
        bookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        bookDate.setCellValueFactory(new PropertyValueFactory<>("bookDate"));
        bookTime.setCellValueFactory(new PropertyValueFactory<>("bookTime"));
        studentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        instructorId.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        lessonName.setCellValueFactory(new PropertyValueFactory<>("lessonName"));
        timePeriod.setCellValueFactory(new PropertyValueFactory<>("timePeriod"));
        rescheduleReason.setCellValueFactory(new PropertyValueFactory<>("rescheduleReason"));
        action.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));

        tblBook.setItems(cartTMS);
    }

    private void loadStudentIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> studentIds = studentsModel.getAllStudentIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(studentIds);
        cmbStudentId.setItems(observableList);
    }

    private void loadInstructorIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> instructorIds = instructorsModel.getAllInstructorIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(instructorIds);
        cmbInstructor.setItems(observableList);
    }

    private void loadLessons() throws SQLException, ClassNotFoundException {
        ArrayList<String> lessons = lessonsModel.getAlllessons();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(lessons);
        cmbLessons.setItems(observableList);
    }

    public void addTableOnAction(ActionEvent event) {
        String bookId = lblBokk.getText();
        Date bookDate = Date.valueOf(txtDate.getText());
        String bookTime = txtTime.getText();
        String studentId = cmbStudentId.getValue();

        if (studentId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select student..!").show();
        }
        String instructorId = cmbInstructor.getValue();

        if (instructorId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select instructor..!").show();
        }
        String lessons = cmbLessons.getValue();
        if (lessons == null) {
            new Alert(Alert.AlertType.ERROR, "Please select lesson..!").show();
        }
        String timePeriod = txtTimePeriod.getText();
        String reason = txtReason.getText();
        Button btn = new Button("Remove");

        CartTM newCartTM = new CartTM(
                bookId,
                bookDate,
                bookTime,
                studentId,
                instructorId,
                lessons,
                timePeriod,
                reason,
                btn
        );
        btn.setOnAction(actionEvent -> {
            cartTMS.remove(newCartTM);
            tblBook.refresh();
        });
        cartTMS.add(newCartTM);
    }

    public void placeBookingOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (tblBook.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please add data..!").show();
        }
        if (cmbStudentId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select student for place booking..!").show();
        }
        if (cmbLessons.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select lesson for place booking..!").show();
        }
        if (cmbInstructor.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select instructor for place booking..!").show();
        }
        String bookId = lblBokk.getText();
        Date bookDate = Date.valueOf(txtDate.getText());
        String bookTime = txtTime.getText();
        String timePeriod = txtTimePeriod.getText();
        String rescheduleReason = txtReason.getText();

        ArrayList<BookingDetailsDto> bookingDetailsDTOS = new ArrayList<>();

        for (CartTM cartTM : cartTMS) {

            BookingDetailsDto bookingDetailsDTO = new BookingDetailsDto(
                    bookId,
                    cartTM.getStudentId()
            );
            bookingDetailsDTOS.add(bookingDetailsDTO);
        }
        ArrayList<LessonsDto> lessonsDTOS = new ArrayList<>();
        for (CartTM cartTM : cartTMS){
            LessonsDto lessonsDTO = new LessonsDto(
                    cartTM.getLessonName(),
                    timePeriod,
                    cartTM.getStudentId(),
                    cartTM.getInstructorId()
            );
            lessonsDTOS.add(lessonsDTO);
        }
        ArrayList<ChooseTrainerDto> chooseTrainerDTOS = new ArrayList<>();
        for(CartTM cartTM:cartTMS){
            ChooseTrainerDto chooseTrainerDTO = new ChooseTrainerDto(
                    bookId,
                    cartTM.getInstructorId()
            );
            chooseTrainerDTOS.add(chooseTrainerDTO);
        }
            BookingDto bookingDto = new BookingDto(
                bookId,
                bookDate,
                bookTime,
                rescheduleReason,
                bookingDetailsDTOS,
                lessonsDTOS,
                chooseTrainerDTOS
        );

        boolean isSaved = bookingModel.saveBooking(bookingDto);
        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Booking saved..!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Booking fail..!").show();
        }
    }
}


