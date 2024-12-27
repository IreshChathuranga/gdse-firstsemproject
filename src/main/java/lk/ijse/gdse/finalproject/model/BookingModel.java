package lk.ijse.gdse.finalproject.model;

import lk.ijse.gdse.finalproject.db.DBConnection;
import lk.ijse.gdse.finalproject.dto.BookingDto;
import lk.ijse.gdse.finalproject.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingModel {
    private final BookingDetailsModel bookingDetailsModel= new BookingDetailsModel();
    private final LessonsModel lessonsModel= new LessonsModel();
    private final ChooseTrainerModel chooseTrainerModel = new ChooseTrainerModel();
public boolean saveBooking(BookingDto bookingDto) throws SQLException, ClassNotFoundException {
    Connection connection = DBConnection.getInstance().getConnection();
    try {
        connection.setAutoCommit(false);
        boolean isBookingSaved = CrudUtil.execute(
                "insert into booking values (?, ?, ?, ?)",
                bookingDto.getBookId(), bookingDto.getBookDate(), bookingDto.getBookTime(), bookingDto.getRescheduleReason());

        if (isBookingSaved) {
            boolean isBookingDetailsSaved = bookingDetailsModel.saveBookingDetaileList(bookingDto.getBookingDetailsDTOS());
            if (isBookingDetailsSaved) {
                boolean isLessonsSaved = lessonsModel.saveLessonList(bookingDto.getLessonsDTOS());
                if (isLessonsSaved) {
                    boolean isChooseTrainerSaved =  chooseTrainerModel.saveChooseTrainer(bookingDto.getChooseTrainerDTOS());
                    if(isChooseTrainerSaved){
                        connection.commit();
                        return true;
                    }
                }
            }
        }
        connection.rollback();
        return false;
    } catch (SQLException e) {
        connection.rollback();
        e.printStackTrace();
        return false;
    } finally {
        connection.setAutoCommit(true);
    }
}
    public String getNextBookingId() throws SQLException, ClassNotFoundException {
        ResultSet rst=CrudUtil.execute("select book_id from booking order by book_id desc limit 1");
        if(rst.next()){
           String lastId = rst.getString(1);
           String subString = lastId.substring(1);
           int i = Integer.parseInt(subString);
            int newIdIndex = i+1;
            return String.format("B%03d",newIdIndex);
       }
        return  "B001";

    }
}
