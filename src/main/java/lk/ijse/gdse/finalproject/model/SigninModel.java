package lk.ijse.gdse.finalproject.model;

import lk.ijse.gdse.finalproject.dto.SigninDto;
import lk.ijse.gdse.finalproject.util.CrudUtil;

import java.sql.SQLException;

public class SigninModel {
    public boolean saveAdmin(SigninDto signinDto) throws SQLException, ClassNotFoundException {
        Boolean isSaved = CrudUtil.execute("insert into signup values(?,?,?,?,?)",
                signinDto.getName(),
                signinDto.getUserName(),
                signinDto.getContactNumber(),
                signinDto.getUserAddress(),
                signinDto.getUserPassword()
        );
        return isSaved;
    }
}
