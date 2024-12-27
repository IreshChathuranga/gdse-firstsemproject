package lk.ijse.gdse.finalproject.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LessonsTM {
    private String lessonName;
    private String timePeriod;
    private String studentId;
    private String instructorId;
}
