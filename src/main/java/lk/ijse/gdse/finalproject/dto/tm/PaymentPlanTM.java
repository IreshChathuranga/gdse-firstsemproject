package lk.ijse.gdse.finalproject.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentPlanTM {
    private String payplanId;
    private double amount;
    private int rate;
    private double ratePrice;
    private String description ;
    private String payId;
}
