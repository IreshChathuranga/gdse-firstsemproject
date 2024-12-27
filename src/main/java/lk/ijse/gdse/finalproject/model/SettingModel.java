package lk.ijse.gdse.finalproject.model;

import lk.ijse.gdse.finalproject.dto.SettingDto;
import lk.ijse.gdse.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingModel {
    public SettingDto getSignupDetails() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from signup LIMIT 1");
        if (rst.next()) {
            return new SettingDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }
    public boolean editSignup(SettingDto settingDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update signup set user_name=?, contact_number=?,  user_address=?, userpassword=? where username=?",
                settingDto.getName(),
                settingDto.getContactNumber(),
                settingDto.getAddress(),
                settingDto.getPassword(),
                settingDto.getUserName()
        );
    }
}
