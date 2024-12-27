package lk.ijse.gdse.finalproject.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SettingDto {
    private String name;
    private String userName;
    private int contactNumber;
    private String address;
    private String password;
}
