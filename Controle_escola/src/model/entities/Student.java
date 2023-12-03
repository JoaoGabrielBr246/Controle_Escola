package model.entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Student implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String name;
    private Integer id;
    private Double note1;
    private Double note2;
    private Double note3;
    private Double note4;

    private ClassStudents classStudents;

    public Student(String name, Integer id, ClassStudents classStudents) {
        this.name = name;
        this.id = id;
        this.classStudents = classStudents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getNote1() {
        return note1;
    }

    public void setNote1(Double note1) {
        this.note1 = note1;
    }

    public Double getNote2() {
        return note2;
    }

    public void setNote2(Double note2) {
        this.note2 = note2;
    }

    public Double getNote3() {
        return note3;
    }

    public void setNote3(Double note3) {
        this.note3 = note3;
    }

    public Double getNote4() {
        return note4;
    }

    public void setNote4(Double note4) {
        this.note4 = note4;
    }

    public ClassStudents getClassStudents() {
        return classStudents;
    }

    public void setClassStudents(ClassStudents classStudents) {
        this.classStudents = classStudents;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", note1=" + note1 +
                ", note2=" + note2 +
                ", note3=" + note3 +
                ", note4=" + note4 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Student() {

    }
}
