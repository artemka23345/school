package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/faculty")

public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }


    @GetMapping("{id}")
    @Operation(summary = "Получить инфо по факультету по ID ")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);

        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    @Operation(summary = "Создать факультет")
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @PutMapping
    @Operation(summary = "Изменить факультет")
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.editFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удалить факультет по ID")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Найти факультет по цвету")
    public ResponseEntity<Collection<Faculty>> findFacultiesColor(@RequestParam(required = false) String color) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.findByColor(color));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/findFacultyByColorOrName")
    @Operation(summary = "Получить факультет по цвету или имени")
    public ResponseEntity<Collection<Faculty>> findByNameOrColor(@RequestParam(required = false) String color, @RequestParam(required = false) String name) {
        Collection<Faculty> facultyToFind = facultyService.findByNameOrColor(color, name);
        if (facultyToFind == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyService.findByNameOrColor(color, name));

    }

    @GetMapping("/getAllStudentOfFaculty")
    @Operation(summary = "Получить все хстудентов факультетта")
    public ResponseEntity<Collection<Student>> getAllStudentOfFaculty(@RequestParam long id) {
        Collection<Student> studentOfFaculty = facultyService.getAllStudentOfFaculty(id);
        if (studentOfFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentOfFaculty);

    }
}

