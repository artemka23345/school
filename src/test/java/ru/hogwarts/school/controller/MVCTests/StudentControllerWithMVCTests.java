package ru.hogwarts.school.controller.MVCTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;
import ru.hogwarts.school.service.impl.StudentServiceImpl;


import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(StudentController.class)
public class StudentControllerWithMVCTests {
        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private StudentRepository studentRepository;
        @MockBean
        private AvatarRepository avatarRepository;
        @SpyBean
        private StudentServiceImpl studentService;
        @InjectMocks
        private StudentController studentController;

        Long id = 1L;
        String name = "Lexa";
        int age = 10;

        JSONObject studentObject = new JSONObject();
        Student student = new Student();

        private void initializingVariablesForTests() throws JSONException {
            studentObject.put("id", id);
            studentObject.put("name", name);
            studentObject.put("age", age);

            student.setId(id);
            student.setName(name);
            student.setAge(age);
        }

        @Test
        public void createStudentTest() throws Exception {
            initializingVariablesForTests();

            when(studentRepository.save(any(Student.class))).thenReturn(student);
            when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

            mockMvc.perform(MockMvcRequestBuilders
                            .post("/student")
                            .content(studentObject.toString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(id))
                    .andExpect(jsonPath("$.name").value(name))
                    .andExpect(jsonPath("$.age").value(age));

        }
        @Test
        public void getByIdStudentTest() throws Exception {
            initializingVariablesForTests();

            when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/student/{id}", id)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(id))
                    .andExpect(jsonPath("$.name").value(name))
                    .andExpect(jsonPath("$.age").value(age))
                    .andDo(print());
        }

    @Test
    public void updateStudentTest() throws Exception {
        initializingVariablesForTests();

        Student updateStudent = new Student(10l,name, 1 + age,null);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(updateStudent);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //    .content(objectMapper.writeValueAsString(updateStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updateStudent.getId()))
                .andExpect(jsonPath("$.name").value(updateStudent.getName()))
                .andExpect(jsonPath("$.age").value(updateStudent.getAge()))
                .andDo(print());
    }
    @Test
    public void deleteStudentTest() throws Exception {
        initializingVariablesForTests();

        doNothing().when(studentRepository).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/{id}", id))
                .andExpect(status().isOk())
                .andDo(print());

    }
}