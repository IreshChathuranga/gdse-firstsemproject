package lk.ijse.gdse.finalproject.dto.tm;

import lombok.*;

import java.sql.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentsTM {
    private String studentId;
    private String studentName;
    private Date dob;
    private String nic;
    private String studentAddress;
    private Date studentRegisterDate;
    private String gender;
    private double advancePayment;
    private String helpingAids;
    private int phoneNumber;
    private String email ;
    private String adminId;
    private String courseId;
    private String paymentPlanId;
    private String paymentId;
    private String vehicleId;

}
