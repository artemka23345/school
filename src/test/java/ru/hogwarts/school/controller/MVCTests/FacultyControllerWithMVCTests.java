package ru.hogwarts.school.controller.MVCTests;


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
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;

import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class FacultyControllerWithMVCTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;
    @SpyBean
    private FacultyServiceImpl facultyServiceImpl;
    @InjectMocks
    private FacultyController facultyController;

    private final Long id = 1L;
    private final String name = "testFac";
    private final String color = "red";

    private final JSONObject facultyObject = new JSONObject();

    private final Faculty faculty = new Faculty(id,name, color,null);


    private void initializingVariablesForTests() throws JSONException {
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        faculty.setId(id);
    }

    @Test
    public void createFacultyTest() throws Exception {
        initializingVariablesForTests();

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color))
                .andDo(print());
    }

    @Test
    public void getByIdFacultyTest() throws Exception {
        initializingVariablesForTests();

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color))
                .andDo(print());
    }

    @Test
    public void updateFacultyTest() throws Exception {
        initializingVariablesForTests();

        Faculty updateFaculty = new Faculty(12L, "new" + name,color,null);

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));
        when(facultyRepository.save(any(Faculty.class))).thenReturn(updateFaculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //    .content(objectMapper.writeValueAsString(updateFaculty)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updateFaculty.getId()))
                .andExpect(jsonPath("$.name").value(updateFaculty.getName()))
                .andExpect(jsonPath("$.color").value(updateFaculty.getColor()))
                .andDo(print());
    }
    @Test
    public void deleteFacultyTest() throws Exception {
        initializingVariablesForTests();

        doNothing().when(facultyRepository).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/{id}", id))
                .andExpect(status().isOk())
                .andDo(print());

    }

}
