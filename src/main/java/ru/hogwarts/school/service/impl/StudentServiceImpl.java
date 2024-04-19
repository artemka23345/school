package ru.hogwarts.school.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exeption.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private Integer count = 1;


    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudentById(long id) {
        return studentRepository.findById(id)
                .orElseThrow(StudentNotFoundException::new);
    }

    public Student editStudent(Student student) {
        log.info("Edit student");
        return studentRepository.save(student);

    }

    public void deleteStudent(long id) {
        log.info("Delete student");
        studentRepository.deleteById(id);
    }

    public Collection<Student> findStudentByAge(int age) {
        log.info("Find student by age");
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findByAgeBetween(int max, int min) {
        log.info("Find student by age between");
        return studentRepository.findByAgeBetween(max, min);
    }

    public Faculty findFacultyOfStudent(long id) {
        log.info("Find faculty of student");
        return findStudentById(id).getFaculty();
    }

    @Override
    public Integer getAmountOfStudents() {
        log.info("Get amount of students");
        return studentRepository.amountOfStudents();
    }

    public Double getAverageAge() {
        log.info("Get average age");
        return studentRepository.averageAge();
    }

    public List<Student> getLastStudents() {
        log.info("Get last students");
        return studentRepository.getLastStudents();
    }

    public List<String> getStudentsNameStartsWith(String letter) {
        List<Student> exp = studentRepository.findAll();
        log.info("Get student name starts with");
        List<String> names = exp
                .stream()
                .map(Student::getName)
                .filter(name -> name.toUpperCase().startsWith(letter))
                .sorted()
                .toList();

        return names;
    }
    public double getAverageAgeWithStream () {
        List <Student> students =studentRepository.findAll();
        log.info("Get average age with stream");
        return students
                .stream()
                .mapToDouble (Student::getAge)
                .average()
                .orElse(0);
    }
    public void printParallel() {
        List<Student> students = studentRepository.findAll();
        log.info("Printing parallel students");
        System.out.println(students.get(0).getName());
        System.out.println(students.get(1).getName());

        new Thread(() -> {
            System.out.println(students.get(2).getName());
            System.out.println(students.get(3).getName());
        }).start();

        new Thread(() -> {
            System.out.println(students.get(4).getName());
            System.out.println(students.get(5).getName());
        }).start();

    }
    public void printSynchronized() {
        List<Student> students = studentRepository.findAll();
log.info("Printing synchronized students");
        printStudentName(students);
        printStudentName(students);

        new Thread(() -> {
            printStudentName(students);
            printStudentName(students);
        }).start();

        new Thread(() -> {
            printStudentName(students);
            printStudentName(students);
        }).start();
    }

    private synchronized void printStudentName(List<Student> students) {
        log.info("Printing student name");
        System.out.println(count + students.get(count).getName());
        count++;
    }
}
