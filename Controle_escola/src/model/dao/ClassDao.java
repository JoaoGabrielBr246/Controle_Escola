package model.dao;
import model.entities.Student;

import java.sql.SQLException;
import java.util.List;


public interface ClassDao {
    void insert(Student obj) throws SQLException;
    void update(Student obj) throws SQLException;
    void deleteById(Integer id) throws SQLException;
    Student findById(Integer id) throws SQLException;
    List<Student> findAll() throws SQLException;
}
