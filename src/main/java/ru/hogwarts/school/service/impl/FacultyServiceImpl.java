package ru.hogwarts.school.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exeption.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;



    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        log.info("Find faculty by id");
        return facultyRepository.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException());
    }

    public Faculty editFaculty(Faculty faculty) {
        log.info("Edit faculty");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        log.info("Delete faculty by id");
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findByColor(String color) {
        log.info("Find faculty by color");
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> findByNameOrColor(String color, String name) {
        log.info("Find faculty by name");
        return facultyRepository.getByNameIgnoreCaseOrColorIgnoreCase(color, name);
    }

    public Collection<Student> getAllStudentOfFaculty(long id) {
        log.info("Find all student  of faculty by id");
        return facultyRepository.getReferenceById(id).getStudents();
    }
}

