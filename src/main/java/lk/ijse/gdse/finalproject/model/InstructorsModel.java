package lk.ijse.gdse.finalproject.model;

import lk.ijse.gdse.finalproject.dto.InstructorsDto;
import lk.ijse.gdse.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InstructorsModel {
    public String getNextInstructorId() throws SQLException, ClassNotFoundException {
        ResultSet rst=CrudUtil.execute("select instru_id from instructor order by instru_id desc limit 1");
        if(rst.next()){
            String lastId = rst.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIdIndex = i+1;
            return String.format("I%03d",newIdIndex);
        }
        return  "I001";

    }

    public ArrayList<InstructorsDto> getAllInstructors() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from instructor");
        ArrayList<InstructorsDto> instructorsDtos = new ArrayList<>();
        while (rst.next()){
            InstructorsDto instructorsDto =  new InstructorsDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6));
            instructorsDtos.add(instructorsDto);
        }
        return instructorsDtos;
    }
    public boolean saveInstructor(InstructorsDto instructorsDto) throws SQLException, ClassNotFoundException {
        Boolean isSaved=CrudUtil.execute("insert into instructor values(?,?,?,?,?,?)", instructorsDto.getInstructorId(),instructorsDto.getInstructorName(),instructorsDto.getInstructorAge(),instructorsDto.getInstructorAddress(),instructorsDto.getCertificationDetail(),instructorsDto.getAdminId());

        return  isSaved;
    }
    public boolean deleteInstructor(String instructorId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from instructor where instru_id=?", instructorId);
    }
    public boolean updateInstructor(InstructorsDto instructorsDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update instructor set  instru_name=?, instru_age=?,  instru_address=?,  certification_detail=?, admin_id=? where instru_id=?",
                instructorsDto.getInstructorName(),
                instructorsDto.getInstructorAge(),
                instructorsDto.getInstructorAddress(),
                instructorsDto.getCertificationDetail(),
                instructorsDto.getAdminId(),
                instructorsDto.getInstructorId()
        );
    }
    public ArrayList<String> getAllInstructorIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select instru_id from instructor");

        ArrayList<String> instructorIds = new ArrayList<>();

        while (rst.next()){
            instructorIds.add(rst.getString(1));
        }

        return instructorIds;
    }
    public InstructorsDto findById(String selectedInstructorId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from instructor where instru_id=?", selectedInstructorId);

        if (rst.next()) {
            return new InstructorsDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
        }
        return null;
    }
}
