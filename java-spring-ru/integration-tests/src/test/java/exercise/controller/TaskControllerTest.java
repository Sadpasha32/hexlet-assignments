package exercise.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

import org.instancio.Instancio;
import org.instancio.Select;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import exercise.repository.TaskRepository;
import exercise.model.Task;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;


    @Test
    public void testWelcomePage() throws Exception {
        var result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThat(body).contains("Welcome to Spring!");
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }


    private Task generateTask() {
        return Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getTitle), () -> faker.lorem().word())
                .supply(Select.field(Task::getDescription), () -> faker.lorem().paragraph())
                .create();
    }

    @Test
    void testGetById() throws Exception {
        Task resTask = createAndCheckTask();
        mockMvc.perform(get("/tasks/" + resTask.getId()))
                .andExpect(MockMvcResultMatchers.content().json(om.writeValueAsString(resTask)));
    }

    @Test
    void testCreate() throws Exception {
        createAndCheckTask();
    }

    @Test
    void testUpdate() throws Exception {
        Task resTask = createAndCheckTask();
        resTask.setTitle("New Title");
        mockMvc.perform(MockMvcRequestBuilders.put("/tasks/" + resTask.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(resTask)));
        mockMvc.perform(get("/tasks/" + resTask.getId()))
                .andExpect(MockMvcResultMatchers.content().json(om.writeValueAsString(resTask)))
                .andExpect(status().isOk());

    }

    @Test
    void testDelete() throws Exception {
        Task resTask = createAndCheckTask();
        mockMvc.perform(delete("/tasks/" + resTask.getId()))
                .andExpect(status().isOk());
        mockMvc.perform(get("/tasks/" + resTask.getId()))
                .andExpect(status().isNotFound());
    }

    private Task createAndCheckTask() throws Exception {
        Task task = generateTask();
        var result = mockMvc.perform(MockMvcRequestBuilders.post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(task)))
                .andExpect(status().isCreated())
                .andReturn();
        return om.readValue(result.getResponse().getContentAsString(), Task.class);
    }

}
