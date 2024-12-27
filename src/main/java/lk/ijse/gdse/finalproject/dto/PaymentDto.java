package lk.ijse.gdse.finalproject.dto;

import lk.ijse.gdse.finalproject.controller.DashboardController;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentDto {
    private String payId;
    private Date payDate;
    private String payMethod;
    private String adminId;
}
