package ru.hogwarts.school.controller;

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
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void contextLoads() throws Exception {
        assertThat(studentController).isNotNull();
    }


    @Test
    public void testFindStudentById() throws Exception {
        Student testStudent = new Student();
        testStudent.setName("Lexa");
        studentController.createStudent(testStudent);

        Student actual = this.testRestTemplate.getForObject("http://localhost:" + port + "/student/" + testStudent.getId(), Student.class);

        assertThat(actual).isEqualTo(testStudent);
        studentController.deleteStudent(testStudent.getId());
    }

    @Test
    public void testFindStudentByAge() throws Exception {
        Student testStudent2 = new Student();
        testStudent2.setAge(4);
        testStudent2.setName("Lexa");
        studentController.createStudent(testStudent2);

        assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/findStudentByAge/" + testStudent2.getAge(), String.class))
                .isNotNull();
        assertThat(testStudent2.getName()).isEqualTo("Lexa");
        studentController.deleteStudent(testStudent2.getId());
    }

    @Test
    public void testFindStudentByAgeBetween() throws Exception {
        Student student1 = new Student(10L, "Lexa", 40,null);
        studentController.createStudent(student1);
        Student student2 = new Student(11L, "Sasha", 88,null);
        studentController.createStudent(student2);
        Student student3 = new Student(12L, "Dima", 60,null);
        studentController.createStudent(student3);


        var result = testRestTemplate.getForObject("http://localhost:" + port + "/student/findStudentByAgeBetween?min= 50&max=70", String.class);
        assertThat(result).isNotNull();


        studentController.deleteStudent(student1.getId());
        studentController.deleteStudent(student2.getId());
        studentController.deleteStudent(student3.getId());

    }

    @Test
    public void testFindFacultyOfStudent() throws Exception {
        Faculty faculty1 = new Faculty(3L, "Lexa", "red",null);
        facultyController.createFaculty(faculty1);
        Student student1 = new Student(1000L, "Dima", 40,faculty1);
        studentController.createStudent(student1);
        System.out.println("student1 = " + student1);
        Faculty actual = this.testRestTemplate.getForObject("http://localhost:" + port + "/student/facultyOfStudent/" + student1.getId(), Faculty.class);
        assertThat(actual.getId()).isEqualTo(faculty1.getId());

        studentController.deleteStudent(student1.getId());
        facultyController.deleteFaculty(faculty1.getId());

    }

    @Test
    public void testCreateStudent() throws Exception {
        Student student = new Student(809l,"Alex",13,null);

        assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/student/createStudent", student, Student.class))
                .isNotNull();
        assertThat(student.getName()).isEqualTo("Alex");

        studentController.deleteStudent(student.getId());
    }

    @Test
    public void testEditStudent() throws Exception {

        Student student1 = new Student();
        student1.setAge(64);
        student1.setName("Alex");

        studentController.createStudent(student1);
        Student student2 = new Student();
        student2.setId(student1.getId());
        student2.setAge(78);
        student2.setName("Lexa");

        ResponseEntity<Student> response = testRestTemplate.exchange("http://localhost:" + port + "/student",
                HttpMethod.PUT, new HttpEntity<>(student2), Student.class);

        Assertions
                .assertThat(response.getBody().getName()).isEqualTo("Lexa");

        studentController.deleteStudent(student2.getId());

    }

    @Test
    void testDeleteStudent() throws Exception {
        Student student1 = new Student();
        student1.setId(20L);
        student1.setName("Lexa");
        studentController.createStudent(student1);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete("http://localhost:" + port + "/student/" + student1.getId());
    }
}
