package lk.ijse.gdse.finalproject.dto.tm;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentTM {
    private String payId;
    private Date payDate;
    private String payMethod;
    private String adminId;
}
