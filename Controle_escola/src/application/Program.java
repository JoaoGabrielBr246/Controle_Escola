package application;

import db.DB;
import model.dao.DaoFactory;
import model.dao.StudentDao;
import model.dao.impl.StudentDaoJDBC;
import model.entities.ClassStudents;
import model.entities.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws SQLException {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);


        System.out.println("O que deseja realizar? ");
        System.out.println("1-) Cadastrar um(a) novo(a) aluno(a)");
        System.out.println("2-) Ver alunos de uma determinada sala");
        System.out.println("3-) Adicionar nota");
        System.out.println("4-) Verificar médias");
        System.out.println("5-) Deletar aluno(a)");

        System.out.print("Digite a opção: ");
        Integer op = sc.nextInt();


        if (op == 1) {
            sc.nextLine();
            System.out.print("Digite o nome do(a) aluno(a): ");
            String name = sc.nextLine();
            System.out.print("Digite a série Ex. (9):  ");
            Integer grade = sc.nextInt();
            System.out.print("Digite a turma Ex. (A): ");
            sc.nextLine();
            String classStudent = sc.nextLine();

            try (Connection connection = DB.getConnection()) {
                StudentDao studentDao = DaoFactory.createStudentDao();
                Student newStudent = new Student(name, 0, new ClassStudents(grade, classStudent));
                studentDao.insert(newStudent);

                System.out.println("Estudante inserido com sucesso! ID gerado: " + newStudent.getId());
            } catch (SQLException e) {
                e.printStackTrace();

            }
        } else if (op == 2) {
            System.out.print("Digite a série Ex. (9): ");
            Integer grade = sc.nextInt();
            System.out.print("Digite a turma Ex. (A): ");
            sc.nextLine();
            String classLetter = sc.nextLine();

            try (Connection connection = DB.getConnection()) {
                StudentDao studentDao = DaoFactory.createStudentDao();
                List<Student> students = studentDao.findByClassAndGrade(grade, classLetter);

                for (Student student : students) {
                    System.out.println(student);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (op == 3) {
            System.out.print("Digite o ID do(a) aluno(a): ");
            int studentId = sc.nextInt();
            sc.nextLine();  // Consumir a quebra de linha pendente

            try (Connection connection = DB.getConnection()) {
                StudentDao studentDao = DaoFactory.createStudentDao();
                Student studentToUpdate = studentDao.findById(studentId);

                if (studentToUpdate != null) {
                    // O aluno foi encontrado, agora atualize as notas
                    System.out.print("Digite a nota 1: ");
                    Double n1 = sc.nextDouble();
                    System.out.print("Digite a nota 2: ");
                    Double n2 = sc.nextDouble();
                    System.out.print("Digite a nota 3: ");
                    Double n3 = sc.nextDouble();
                    System.out.print("Digite a nota 4: ");
                    Double n4 = sc.nextDouble();

                    // Atualize as notas no objeto Student
                    studentToUpdate.setNote1(n1);
                    studentToUpdate.setNote2(n2);
                    studentToUpdate.setNote3(n3);
                    studentToUpdate.setNote4(n4);

                    // Agora, chame o método updateGrades para atualizar as notas no banco de dados
                    studentDao.updateGrades(studentToUpdate);

                    System.out.println("Notas atualizadas com sucesso!");
                } else {
                    System.out.println("Aluno(a) não encontrado(a).");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (op == 4) {
            System.out.print("Digite a série Ex. (9): ");
            Integer grade = sc.nextInt();
            System.out.print("Digite a turma Ex. (A): ");
            sc.nextLine();
            String classLetter = sc.nextLine();

            try (Connection connection = DB.getConnection()) {
                StudentDao studentDao = DaoFactory.createStudentDao();

                // Calcula a média
                Double average = studentDao.calculateAverageByClassAndGrade(grade, classLetter);

                if (average != null) {
                    System.out.println("Média da turma: " + average);

                    // Obtém os alunos
                    List<Student> students = studentDao.findByClassAndGrade(grade, classLetter);

                    // Exibe os resultados
                    for (Student student : students) {
                        double studentAverage = (student.getNote1() + student.getNote2() + student.getNote3() + student.getNote4()) / 4;

                        if (studentAverage >= 7) {
                            System.out.println(student.getName() + " - Aprovado(a)");
                        } else {
                            System.out.println(student.getName() + " - Recuperação");
                        }
                    }
                } else {
                    System.out.println("Não há alunos(as) na turma especificada.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (op == 5) {
            System.out.print("Digite o ID do(a) aluno(a) para deletar: ");
            int idDel = sc.nextInt();
            StudentDao studentDao = DaoFactory.createStudentDao();
            studentDao.deleteById(idDel);
            System.out.println("Aluno(a) deletado(a)!");
            sc.close();
        } else {
            System.out.println("Opção inválida!");
        }


        sc.close();
    }
}
