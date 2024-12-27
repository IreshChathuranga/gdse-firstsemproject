package lk.ijse.gdse.finalproject.model;

import lk.ijse.gdse.finalproject.dto.BookingDetailsDto;
import lk.ijse.gdse.finalproject.dto.BookingDto;
import lk.ijse.gdse.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookingDetailsModel {
public boolean saveBookingDetaileList(ArrayList<BookingDetailsDto> bookingDetailsDTOS) throws SQLException, ClassNotFoundException {
    for (BookingDetailsDto bookingDetailsDTO : bookingDetailsDTOS) {
        boolean isBookingDetailsSaved = saveBookingDetails(bookingDetailsDTO);
        if (!isBookingDetailsSaved) {
            return false;
        }
    }
    return true;
}
    private boolean saveBookingDetails(BookingDetailsDto bookingDetailsDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into booking_details values (?, ?)",
                bookingDetailsDto.getBookId(),
                bookingDetailsDto.getStudentId()
        );
    }
}
