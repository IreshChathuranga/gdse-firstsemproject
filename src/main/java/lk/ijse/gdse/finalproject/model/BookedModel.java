package lk.ijse.gdse.finalproject.model;

import lk.ijse.gdse.finalproject.dto.BookedDto;
import lk.ijse.gdse.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookedModel {
    public ArrayList<BookedDto> getAllBooking() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from booking");
        ArrayList<BookedDto> bookedDtos = new ArrayList<>();
        while (rst.next()){
            BookedDto bookedDto = new BookedDto(
                    rst.getString(1),
                    rst.getDate(2),
                    rst.getString(3),
                    rst.getString(4));
            bookedDtos.add(bookedDto);
        }
        return bookedDtos;
    }
}
