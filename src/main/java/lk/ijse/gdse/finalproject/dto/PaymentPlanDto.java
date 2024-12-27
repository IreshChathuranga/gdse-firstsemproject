package lk.ijse.gdse.finalproject.dto;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class PaymentPlanDto {
    private String payplanId;
    private double amount;
    private int rate;
    private double ratePrice;
    private String description ;
    private String payId;
}
