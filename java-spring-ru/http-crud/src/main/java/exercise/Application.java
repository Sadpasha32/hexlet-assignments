package exercise;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import exercise.model.Post;

@SpringBootApplication
@RestController
@RequestMapping("/posts")
public class Application {

    private final List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping
    public List<Post> getAll() {
        return posts;
    }

    @GetMapping("/{id}")
    public Optional<Post> getById(@PathVariable String id) {
        return posts.stream().filter(x -> x.getId().equals(id)).findFirst();
    }

    @PostMapping
    public Post create(@RequestBody Post post) {
        posts.add(post);
        return post;
    }

    @PutMapping("/{id}")
    public Post update(@PathVariable String id, @RequestBody Post post) {
        if (posts.stream().anyMatch(x -> x.getId().equals(id))) {
            posts.removeIf(x -> x.getId().equals(id));
            posts.add(post);
        }
        return post;
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable String id) {
        return posts.removeIf(x -> x.getId().equals(id));
    }
}
