package exercise.controller;

import exercise.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping(path = "")
    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Comment getCommentById(@PathVariable long id) {
        return commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " not found"));
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment saveComment(@RequestBody Comment newComment) {
        commentRepository.save(newComment);
        return newComment;
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Comment updateComment(@PathVariable long id, @RequestBody Comment updComment) {
        var existingComment = commentRepository.findById(id);
        if (existingComment.isPresent()) {
            var updatedComment = existingComment.get();
            updatedComment.setBody(updComment.getBody());
            commentRepository.save(updatedComment);
            return updatedComment;
        } else throw new ResourceNotFoundException(String.format("Comment with id %d not found", id));
    }

    @DeleteMapping(path = "/{id}")
    public void deleteComment(@PathVariable long id) {
        commentRepository.deleteById(id);
    }
}
// END