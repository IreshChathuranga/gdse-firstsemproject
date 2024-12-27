package lk.ijse.gdse.finalproject.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InstructorsDto {
    private String instructorId;
    private String instructorName;
    private int instructorAge;
    private String instructorAddress;
    private String certificationDetail;
    private String adminId;
}
