package exercise.controller;

import java.util.List;

import com.sun.source.util.TaskListener;
import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.mapper.TaskMapper;
import exercise.model.Task;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.TaskRepository;
import jakarta.validation.Valid;

import static java.lang.String.format;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskMapper taskMapper;


    @GetMapping
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @GetMapping("/{id}")
    public TaskDTO getById(@PathVariable Long id) {
        return taskMapper.map(taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Task with id %s not found", id))));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO create(@Valid @RequestBody TaskCreateDTO createDTO) {
        Task task = taskMapper.map(createDTO);
        return taskMapper.map(taskRepository.save(task));
    }

    @PutMapping("/{id}")
    public TaskDTO update(@PathVariable Long id, @Valid @RequestBody TaskUpdateDTO taskUpdateDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Task with id %s not found", id)));
        taskMapper.update(taskUpdateDTO, task);
        if (taskUpdateDTO.getAssigneeId() != null) {
            task.setAssignee(userRepository.findById(taskUpdateDTO.getAssigneeId())
                    .orElseThrow(() -> new ResourceNotFoundException(format("User with id %s not found", taskUpdateDTO.getAssigneeId()))));
        }
        return taskMapper.map(taskRepository.save(task));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }

}
