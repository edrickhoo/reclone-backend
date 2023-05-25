package com.example.reddit.controller;

import com.example.reddit.dto.SubredditDto;
import com.example.reddit.exception.SubredditNotFoundException;
import com.example.reddit.model.Subreddit;
import com.example.reddit.service.SubredditService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditController {

    private final SubredditService subredditService;


    @PostMapping
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody @Valid SubredditDto subredditDto) {
        SubredditDto subreddit = subredditService.save(subredditDto);

        return new ResponseEntity<>(subreddit, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAllSubreddits() {
       return ResponseEntity
               .status(HttpStatus.OK)
               .body(subredditService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubredditDto> getSubreddit(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subredditService.getSubreddit(id));


    }

    @GetMapping(params = "subredditName")
    public ResponseEntity<SubredditDto> getSubredditByName(@RequestParam String subredditName) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(subredditService.getSubredditByName(subredditName));
    }
}
