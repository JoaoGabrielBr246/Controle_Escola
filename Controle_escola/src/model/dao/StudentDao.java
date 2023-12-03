package model.dao;

import model.entities.ClassStudents;
import model.entities.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentDao {
    void insert(Student obj) throws SQLException;

    void updateGrades(Student obj) throws SQLException;

    List<Student> findByClassAndGrade(Integer grade, String classLetter) throws SQLException;

    Student findById(Integer id) throws SQLException;

    Double calculateAverageByClassAndGrade(Integer grade, String classLetter) throws SQLException;

    void deleteById(Integer id) throws SQLException;
}
