package lk.ijse.gdse.finalproject.model;

import lk.ijse.gdse.finalproject.dto.SalaryDto;
import lk.ijse.gdse.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SalaryModel {
    public String getNextSalaryId() throws SQLException, ClassNotFoundException {
        ResultSet rst= CrudUtil.execute("select salary_id from salary order by salary_id desc limit 1");
        if(rst.next()){
            String lastId = rst.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIdIndex = i+1;
            return String.format("L%03d",newIdIndex);
        }
        return  "L001";

    }
    public ArrayList<SalaryDto> getAllSalary() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from salary");
        ArrayList<SalaryDto> salaryDtos = new ArrayList<>();
        while (rst.next()){
            SalaryDto salaryDto = new SalaryDto(
                    rst.getString(1),
                    rst.getDouble(2),
                    rst.getDate(3),
                    rst.getInt(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7));
            salaryDtos.add(salaryDto);
        }
        return salaryDtos;
    }
    public boolean saveSalary(SalaryDto salaryDto) throws SQLException, ClassNotFoundException {
        Boolean isSaved=CrudUtil.execute("insert into salary values(?,?,?,?,?,?,?)", salaryDto.getSalaryId(),salaryDto.getAmount(),salaryDto.getPayDay(),salaryDto.getHolidays(),salaryDto.getIsReceived(),salaryDto.getAdminId(),salaryDto.getStafId());

        return  isSaved;
    }
    public boolean deleteSalary(String salaryId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from salary where salary_id=?", salaryId);
    }
    public boolean updateSalary(SalaryDto salaryDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update salary set amount=?, pay_day=?, holidays=?, is_received=?, admin_id=?, staf_id=? where salary_id=?",
                salaryDto.getAmount(),
                salaryDto.getPayDay(),
                salaryDto.getHolidays(),
                salaryDto.getIsReceived(),
                salaryDto.getAdminId(),
                salaryDto.getStafId(),
                salaryDto.getSalaryId()
        );
    }
}
