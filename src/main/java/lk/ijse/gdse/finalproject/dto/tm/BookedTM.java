package lk.ijse.gdse.finalproject.dto.tm;

import lombok.*;

import java.sql.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookedTM {
    private String bookId;
    private Date bookDate;
    private String bookTime;
    private String rescheduleReason;
}
