package ru.hogwarts.school.controller;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("{id}")
    @Operation(summary = "Получить инфо студента по ID ")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudentById(id);
        return ResponseEntity.ok(student);
    }

    @PostMapping()
    @Operation(summary = "Создать студента")
    public Student createStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping()
    @Operation(summary = "Изменить студента")
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удалить студента по ID")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    @Operation(summary = "Найти студента по AGE")
    public ResponseEntity<Collection<Student>> findStudentsByAge(@RequestParam int age) {
        if (age > 0) {
            return ResponseEntity.ok(studentService.findStudentByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/findStudentByAgeBetween")
    @Operation(summary = "Фильтр студентов по возрасту min и max")
    public Collection<Student> findByAgeBetween(@RequestParam(required = true) Integer max, @RequestParam(required = true) Integer min) {
        return studentService.findByAgeBetween(min, max);
    }

    @GetMapping("/facultyOfStudent/{id}")
    @Operation(summary = "Найти студента по FacultyID")
    public Faculty findFaculty(@PathVariable long id) {
        return studentService.findFacultyOfStudent(id);
    }

    @GetMapping("/getAmountOfStudents")
    @Operation(summary = "Получить общее количество студентов ")
    public ResponseEntity<Integer> AmountOfStudents() {
        if (studentService.getAmountOfStudents() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.getAmountOfStudents());
    }
    @GetMapping("/getAverageAgeOfStudents")
    @Operation(summary = "Получить средний возраст всех студентов ")
    public ResponseEntity<Double> getAverageAgeOfStudents() {
        if (studentService.getAverageAge() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.getAverageAge());
    }
    @GetMapping("/getLastStudents")
    @Operation(summary = "Получить последних 5 студентов из БД")
    public ResponseEntity<List<Student>> getLastStudents() {
        if (studentService.getLastStudents().isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.getLastStudents());
    }
}
