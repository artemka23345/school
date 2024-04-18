package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;


public interface StudentService {
    Student addStudent(Student student);

    Student findStudentById(long id);

    Student editStudent(Student student);

    void deleteStudent(long id);

    Collection<Student> findStudentByAge(int age);

    Collection<Student> findByAgeBetween(int min, int max);

    Faculty findFacultyOfStudent(long id);

    Integer getAmountOfStudents ();

    Double getAverageAge ();
    List<Student> getLastStudents();
    List<String> getStudentsNameStartsWith(String letter);
    double getAverageAgeWithStream ();
    void printParallel();
    void printSynchronized();

}
