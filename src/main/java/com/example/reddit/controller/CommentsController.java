package com.example.reddit.controller;

import com.example.reddit.dto.CommentsDto;
import com.example.reddit.service.CommentsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
@Slf4j
public class CommentsController {
    private final CommentsService commentsService;

    @PostMapping
    public ResponseEntity createComments(@RequestBody @Valid CommentsDto commentsDto) {
        commentsService.createComments(commentsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(params = "postId")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@RequestParam Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentsService.getAllCommentsForPost(postId));
    }

    @GetMapping(params = "userName")
    public ResponseEntity<List<CommentsDto>> getCommentsByUsername(@RequestParam String userName) {
        return ResponseEntity.status(HttpStatus.OK).body(commentsService.getCommentsByUsername(userName));
    }
}
