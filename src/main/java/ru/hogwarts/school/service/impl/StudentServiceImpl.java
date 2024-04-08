package ru.hogwarts.school.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exeption.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudentById(long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException());
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
    public Double getAverageAge () {
        log.info("Get average age");
        return studentRepository.averageAge();
    }
    public List<Student> getLastStudents() {
        log.info("Get last students");
        return studentRepository.getLastStudents();
    }


}
