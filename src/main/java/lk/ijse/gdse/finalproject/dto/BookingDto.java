package lk.ijse.gdse.finalproject.dto;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingDto {
    private String bookId;
    private Date bookDate;
    private String bookTime;
    private String rescheduleReason;
    private ArrayList<BookingDetailsDto> bookingDetailsDTOS;
    private ArrayList<LessonsDto> lessonsDTOS;
    private ArrayList<ChooseTrainerDto> chooseTrainerDTOS;
}
