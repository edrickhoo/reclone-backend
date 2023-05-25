package com.example.reddit.controller;

import com.example.reddit.dto.PostDto;
import com.example.reddit.dto.PostResponse;
import com.example.reddit.service.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private PostService postService;

    @PostMapping
    public ResponseEntity<Object> createPost (@RequestBody @Valid PostDto postDto) {
        postService.save(postDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping()
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping(params = "subredditId")
    public List<PostResponse> getPostsBySubreddit(@RequestParam Long subredditId) {
        return postService.getPostsBySubreddit(subredditId);
    }

    @GetMapping(params = "subredditName")
    public List<PostResponse> getPostsBySubredditName(@RequestParam String subredditName) {
        return postService.getPostsBySubredditName(subredditName);
    }

    @GetMapping(params = "username")
    public List<PostResponse> getPostsByUsername(@RequestParam String username) {
        return postService.getPostsByUsername(username);
    }



}
