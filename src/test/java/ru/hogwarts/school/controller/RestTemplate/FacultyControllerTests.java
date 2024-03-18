package ru.hogwarts.school.controller.RestTemplate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    FacultyRepository facultyRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    public void testFindFacultyById() throws Exception {
        Faculty testFaculty = new Faculty();
        testFaculty.setName("testFac");
        facultyController.createFaculty(testFaculty);

        Faculty actual = this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty/" + testFaculty.getId(), Faculty.class);
        Assertions
                .assertThat(actual.getName()).isEqualTo("testFac");
        facultyController.deleteFaculty(testFaculty.getId());
    }

    @Test
    public void testCreateFaculty() throws Exception {
        Faculty faculty = new Faculty(10L, "testFac", "red", null);

        Faculty actual = this.testRestTemplate.postForObject("http://localhost:" + port + "/faculty/createFaculty", faculty, Faculty.class);

        Assertions
                .assertThat(actual)
                .isNotNull();

        facultyController.deleteFaculty(faculty.getId());
    }

    @Test
    public void testEditFaculty() throws Exception {
        Faculty faculty1 = new Faculty();
        faculty1.setName("testFac");
        faculty1.setColor("red");
        faculty1.setStudents(null);
        facultyRepository.save(faculty1);
        Faculty faculty2 = new Faculty();
        faculty2.setId(faculty1.getId());
        faculty2.setName("testFac2");
        faculty2.setColor("green");
        faculty2.setStudents(null);

        ResponseEntity<Faculty> response = testRestTemplate.exchange("http://localhost:" + port + "/faculty/editFaculty",
                HttpMethod.PUT,
                new HttpEntity<>(faculty2),
                Faculty.class);

        Assertions
                .assertThat(response.getStatusCode().is2xxSuccessful());
        facultyRepository.deleteById(faculty2.getId());
    }

    @Test
    void testDeleteFaculty() throws Exception {
        Faculty faculty1 = new Faculty();
        faculty1.setId(20L);
        faculty1.setName("testFac");
        facultyController.createFaculty(faculty1);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete("http://localhost:" + port + "/faculty/" + faculty1.getId());
    }

    @Test
    public void testFindFacultyByColorOrName() throws Exception {
        Faculty facultyTest = new Faculty();
        facultyTest.setId(20L);
        facultyTest.setName("testFac");
        facultyTest.setColor("red");
        facultyController.createFaculty(facultyTest);

        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty/findFacultyByColorOrName?name=" + facultyTest.getName(), String.class))
                .isNotNull();
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty/findFacultyByColorOrName?color=" + facultyTest.getColor(), String.class))
                .isNotNull();
        facultyController.deleteFaculty(facultyTest.getId());
    }

    @Test
    public void testGetAllStudentOfFaculty() throws Exception {
        Faculty facultyTest = new Faculty();
        facultyTest.setId(20L);
        facultyTest.setName("testFac");
        facultyTest.setColor("red");
        facultyController.createFaculty(facultyTest);
        Student student1 = new Student();
        student1.setFaculty(facultyTest);
        Collection<Student> studentCollection = List.of(student1);
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty/getAllFacultyOfFaculty?id=" + facultyTest.getId(), String.class))
                .isNotNull();
        facultyController.deleteFaculty(facultyTest.getId());
    }

}
