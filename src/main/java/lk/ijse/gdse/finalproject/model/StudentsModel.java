package lk.ijse.gdse.finalproject.model;

import lk.ijse.gdse.finalproject.dto.StudentsDto;
import lk.ijse.gdse.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentsModel {
    public String getNextBookingId() throws SQLException, ClassNotFoundException {
        ResultSet rst=CrudUtil.execute("select student_id from student order by student_id desc limit 1");
        if(rst.next()){
            String lastId = rst.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIdIndex = i+1;
            return String.format("S%03d",newIdIndex);
        }
        return  "S001";

    }
    public ArrayList<StudentsDto> getAllStudents() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from student");
        ArrayList<StudentsDto> studentsDtos = new ArrayList<>();
        while (rst.next()){
            StudentsDto studentsDto =  new StudentsDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDate(6),
                    rst.getString(7),
                    rst.getDouble(8),
                    rst.getString(9),
                    rst.getInt(10),
                    rst.getString(11),
                    rst.getString(12),
                    rst.getString(13),
                    rst.getString(14),
                    rst.getString(15),
                    rst.getString(16));
            studentsDtos.add(studentsDto);
        }
        return studentsDtos;
    }
    public boolean saveStudent(StudentsDto studentsDto) throws SQLException, ClassNotFoundException {
        Boolean isSaved = CrudUtil.execute("insert into student values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                studentsDto.getStudentId(),
                studentsDto.getStudentName(),
                studentsDto.getDob(),
                studentsDto.getNic(),
                studentsDto.getStudentAddress(),
                studentsDto.getStudentRegisterDate(),
                studentsDto.getGender(),
                studentsDto.getAdvancePayment(),
                studentsDto.getHelpingAids(),
                studentsDto.getPhoneNumber(),
                studentsDto.getEmail(),
                studentsDto.getAdminId(),
                studentsDto.getCourseId(),
                studentsDto.getPaymentPlanId(),
                studentsDto.getPaymentId(),
                studentsDto.getVehicleId());
        return isSaved;
    }
    public boolean deleteStudent(String studentId) throws SQLException, ClassNotFoundException {
        return  CrudUtil.execute("delete from student where student_id=?", studentId);
    }

    public boolean updateStudent(StudentsDto studentsDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update student set stu_name=?, DOB=?,  NIC=? ,stu_address=? ,register_date=? ,gender=? ,advance_payment=? ,helping_aids=? ,phone_number=? ,email=?, curs_id=? ,paymentPlan_id=? ,veh_id=? where student_id=?",
                studentsDto.getStudentName(),
                studentsDto.getDob(),
                studentsDto.getNic(),
                studentsDto.getStudentAddress(),
                studentsDto.getStudentRegisterDate(),
                studentsDto.getGender(),
                studentsDto.getAdvancePayment(),
                studentsDto.getHelpingAids(),
                studentsDto.getPhoneNumber(),
                studentsDto.getEmail(),
                studentsDto.getCourseId(),
                studentsDto.getPaymentPlanId(),
                studentsDto.getVehicleId(),
                studentsDto.getStudentId());
    }
    public ArrayList<String> getAllStudentIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select student_id from student");

        ArrayList<String> studentIds = new ArrayList<>();

        while (rst.next()){
            studentIds.add(rst.getString(1));
        }

        return studentIds;
    }
    public StudentsDto findById(String selectedStudentId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from student where student_id=?", selectedStudentId);

        if (rst.next()) {
            return new StudentsDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDate(6),
                    rst.getString(7),
                    rst.getDouble(8),
                    rst.getString(9),
                    rst.getInt(10),
                    rst.getString(11),
                    rst.getString(12),
                    rst.getString(13),
                    rst.getString(14),
                    rst.getString(15),
                    rst.getString(16));
        }
        return null;
    }
}
