package lk.ijse.gdse.finalproject.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SigninDto {
    private String name;
    private String userName;
    private int contactNumber;
    private String userAddress;
    private String userPassword;

}
