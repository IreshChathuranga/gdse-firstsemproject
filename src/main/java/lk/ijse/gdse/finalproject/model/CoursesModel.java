package lk.ijse.gdse.finalproject.model;

import lk.ijse.gdse.finalproject.dto.CoursesDto;
import lk.ijse.gdse.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CoursesModel {
    public String getNextCourseId() throws SQLException, ClassNotFoundException {
        ResultSet rst=CrudUtil.execute("select course_id from course order by course_id desc limit 1");
        if(rst.next()){
            String lastId = rst.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIdIndex = i+1;
            return String.format("C%03d",newIdIndex);
        }
        return  "C001";

    }
    public ArrayList<CoursesDto> getAllCourse() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from course");
        ArrayList<CoursesDto> coursesDtos = new ArrayList<>();
        while (rst.next()){
            CoursesDto coursesDto = new CoursesDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3));
            coursesDtos.add(coursesDto);
        }
        return coursesDtos;
    }
    public boolean saveCourse(CoursesDto coursesDto) throws SQLException, ClassNotFoundException {
        Boolean isSaved=CrudUtil.execute("insert into course values(?,?,?)", coursesDto.getCourseId(),coursesDto.getCourseName(),coursesDto.getAdminId());

        return  isSaved;
    }
    public boolean deleteCourse(String courseId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from course where course_id=?", courseId);
    }
    public boolean updateCourse(CoursesDto coursesDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update course set  course_name=?, admin_id=? where course_id=?",
                coursesDto.getCourseName(),
                coursesDto.getAdminId(),
                coursesDto.getCourseId()
        );
    }
}
