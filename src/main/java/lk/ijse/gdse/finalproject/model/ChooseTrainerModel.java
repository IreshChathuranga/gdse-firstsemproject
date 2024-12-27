package lk.ijse.gdse.finalproject.model;

import lk.ijse.gdse.finalproject.dto.ChooseTrainerDto;
import lk.ijse.gdse.finalproject.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class ChooseTrainerModel {
    public boolean saveChooseTrainer(ArrayList<ChooseTrainerDto> chooseTrainerDTOS) throws SQLException, ClassNotFoundException {
        for (ChooseTrainerDto chooseTrainerDTO : chooseTrainerDTOS) {
            boolean isChooseTrainerSaved = saveChooseTrainer(chooseTrainerDTO);
            if (!isChooseTrainerSaved) {
                return false;
            }
        }
        return true;
    }
    public boolean saveChooseTrainer(ChooseTrainerDto chooseTrainerDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into choose_trainer values (?, ?)",
                chooseTrainerDto.getBookId(),
                chooseTrainerDto.getInstructorId());
    }
}
