package lk.ijse.gdse.finalproject.model;

import lk.ijse.gdse.finalproject.dto.LessonsDto;
import lk.ijse.gdse.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LessonsModel {

    public ArrayList<LessonsDto> getAllLessons() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from training_lesson");
        ArrayList<LessonsDto> lessonsDtos = new ArrayList<>();
        while (rst.next()){
            LessonsDto lessonsDto = new LessonsDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4));
            lessonsDtos.add(lessonsDto);
        }
        return lessonsDtos;
    }
    public boolean saveLesson(LessonsDto lessonsDto) throws SQLException, ClassNotFoundException {
        Boolean isSaved=CrudUtil.execute("insert into training_lesson values(?,?,?,?)", lessonsDto.getLessonName(),lessonsDto.getTimePeriod(),lessonsDto.getStudentId(),lessonsDto.getInstructorId());

        return  isSaved;
    }
    public boolean deleteLesson(String lessonName) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from training_lesson where less_name=?", lessonName);
    }
    public boolean updateLesson(LessonsDto lessonsDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update training_lesson set time_period=?, stu_id=?, instruc_id=? where less_name=?",
                lessonsDto.getTimePeriod(),
                lessonsDto.getStudentId(),
                lessonsDto.getInstructorId(),
                lessonsDto.getLessonName()
        );
    }
    public ArrayList<String> getAlllessons() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select less_name from training_lesson");

        ArrayList<String> lessons = new ArrayList<>();

        while (rst.next()){
            lessons.add(rst.getString(1));
        }

        return lessons;
    }
public boolean saveLessonList(ArrayList<LessonsDto> lessonsDTOS) throws SQLException, ClassNotFoundException {
    for (LessonsDto lessonsDTO : lessonsDTOS) {
        boolean isLessonSaved = saveLessons(lessonsDTO);
        if (!isLessonSaved) {
            return false;
        }
    }
    return true;
}

    public boolean saveLessons(LessonsDto lessonsDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into training_lesson values (?, ?, ?, ?)",
                lessonsDto.getLessonName(),
                lessonsDto.getTimePeriod(),
                lessonsDto.getStudentId(),
                lessonsDto.getInstructorId());
    }
}
