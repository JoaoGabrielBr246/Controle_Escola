package model.entities;

import java.io.Serial;
import java.io.Serializable;

public class ClassStudents implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer grade;
    private String classLetter;

    public ClassStudents(Integer grade, String classLetter) {
        this.grade = grade;
        this.classLetter = classLetter;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getClassLetter() {
        return classLetter;
    }

    public void setClassLetter(String classLetter) {
        this.classLetter = classLetter;
    }

    @Override
    public String toString() {
        return "ClassStudents{" +
                "grade=" + grade +
                ", classLetter=" + classLetter +
                '}';
    }

    public ClassStudents() {

    }
}
