package com.example.reddit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class SubredditNotFoundException extends RuntimeException{
    public SubredditNotFoundException(String message) {
        super(message);
    }
}
