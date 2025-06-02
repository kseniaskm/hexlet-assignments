package exercise;

import exercise.model.Post;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public List<Post> getPosts(@RequestParam(defaultValue = "5") Integer limit) {
        return posts.stream().limit(limit).toList();
    }

    @GetMapping("/posts/{id}")
    public Optional<Post> getPostById(@PathVariable String id) {
        return posts.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    @PostMapping("/posts")
    public Post createPost(@RequestBody Post post) {
        posts.add(post);
        return post;
    }

    @PutMapping("/posts/{id}")
    public Post updatePost(@PathVariable String id, @RequestBody Post postUpd) {
        var existingPost = posts.stream().filter(p -> p.getId().equals(id)).findFirst();
        if (existingPost.isPresent()) {
            var updatedPost = existingPost.get();
            updatedPost.setId(postUpd.getId());
            updatedPost.setTitle(postUpd.getTitle());
            updatedPost.setBody(postUpd.getBody());
        }
        return postUpd;
    }

    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable String id){
        posts.removeIf(p -> p.getId().equals(id));
    }
    // END

}
