package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping
    public List<PostDTO> getAllPosts() {
        List<Post> allPosts = postRepository.findAll();
        return allPosts.stream().map(this::toPostDTO).toList();
    }

    @GetMapping("/{id}")
    public PostDTO getById(@PathVariable Long id) {
        if (postRepository.findById(id).isPresent()) {
            return toPostDTO(postRepository.findById(id).get());
        } else {
            throw new ResourceNotFoundException(String.format("Post with id %s not found", id));
        }
    }

    private PostDTO toPostDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setBody(post.getBody());
        postDTO.setTitle(post.getTitle());
        postDTO.setId(post.getId());
        List<Comment> comments = commentRepository.findByPostId(post.getId());
        List<CommentDTO> commentDTOS = comments.stream().map(this::toCommentDTO).toList();
        postDTO.setComments(commentDTOS);
        return postDTO;
    }

    private CommentDTO toCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setBody(comment.getBody());
        commentDTO.setId(comment.getId());
        return commentDTO;
    }

}
