package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.StudentDao;
import model.entities.ClassStudents;
import model.entities.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoJDBC implements StudentDao {
    private Connection conn;
    public StudentDaoJDBC(Connection conn) {
        this.conn = conn;
    }
    @Override
    public void insert(Student obj) throws SQLException {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO students "
                            + "(name, grade, class) "
                            + "VALUES "
                            + "(?,?,?)"
                    , Statement.RETURN_GENERATED_KEYS
            );
            st.setString(1, obj.getName());
            st.setInt(2, obj.getClassStudents().getGrade());
            st.setString(3, obj.getClassStudents().getClassLetter());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected error! No rows affected :(");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }
    @Override
    public void updateGrades(Student obj) throws SQLException {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE students SET note1=?, note2=?, note3=?, note4=? WHERE id=?");
            st.setDouble(1, obj.getNote1());
            st.setDouble(2, obj.getNote2());
            st.setDouble(3, obj.getNote3());
            st.setDouble(4, obj.getNote4());
            st.setInt(5, obj.getId());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected <= 0) {
                throw new DbException("Aluno não encontrado ou nenhum dado foi atualizado");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }
    @Override
    public List<Student> findByClassAndGrade(Integer grade, String classLetter) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT * FROM students WHERE grade = ? AND class = ?");
            st.setInt(1, grade);
            st.setString(2, classLetter);

            rs = st.executeQuery();

            List<Student> list = new ArrayList<>();

            while (rs.next()) {
                list.add(instantiateStudent(rs));
            }

            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    private Student instantiateStudent(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        String name = rs.getString("name");
        Double note1 = rs.getDouble("note1");
        Double note2 = rs.getDouble("note2");
        Double note3 = rs.getDouble("note3");
        Double note4 = rs.getDouble("note4");
        Integer grade = rs.getInt("grade");
        String classLetter = rs.getString("class");

        ClassStudents classStudents = new ClassStudents(grade, classLetter);

        Student student = new Student(name, id, classStudents);
        student.setNote1(note1);
        student.setNote2(note2);
        student.setNote3(note3);
        student.setNote4(note4);

        return student;
    }
    @Override
    public Student findById(Integer id) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT * FROM students WHERE id = ?");
            st.setInt(1, id);

            rs = st.executeQuery();

            if (rs.next()) {
                return instantiateStudent(rs);
            }

            return null;
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    @Override
    public Double calculateAverageByClassAndGrade(Integer grade, String classLetter) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT AVG((note1 + note2 + note3 + note4) / 4) as average " +
                            "FROM students " +
                            "WHERE grade = ? AND class = ?");
            st.setInt(1, grade);
            st.setString(2, classLetter);

            rs = st.executeQuery();

            if (rs.next()) {
                return rs.getDouble("average");
            }

            return null; // Retorna null se não houver alunos na classe e turma especificadas
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    @Override
    public void deleteById(Integer id) throws SQLException {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM students\n" +
                    "WHERE Id = ?");
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }


}
