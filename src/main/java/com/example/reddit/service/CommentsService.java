package com.example.reddit.service;

import com.example.reddit.dto.CommentsDto;
import com.example.reddit.exception.PostNotFoundException;
import com.example.reddit.exception.SpringRedditException;
import com.example.reddit.mapper.CommentsMapper;
import com.example.reddit.model.Comment;
import com.example.reddit.model.Post;
import com.example.reddit.model.User;
import com.example.reddit.repository.CommentRepository;
import com.example.reddit.repository.PostRepository;
import com.example.reddit.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@AllArgsConstructor
public class CommentsService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentsMapper commentsMapper;
    private final UserRepository userRepository;
    private final AuthService authService;

    public void createComments(CommentsDto commentsDto) {
        Post post = postRepository.findById(Long.valueOf(commentsDto.getPostId()))
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId() + " was not found"));
        User user = userRepository.findByUsername(commentsDto.getUserName())
                .orElseThrow(() -> new UsernameNotFoundException(commentsDto.getUserName() + " was not found"));
        System.out.println(authService.getCurrentUser().getUsername());
        if(!commentsDto.getUserName().equals(authService.getCurrentUser().getUsername())) {
            throw new SpringRedditException("ヽ༼ಠل͜ಠ༽ﾉ");
        }
        Comment comment = commentsMapper.map(commentsDto, post, user);
        commentRepository.save(comment);
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId + " id post was not found"));
        List<Comment> comments = commentRepository.findAllByPost(post);

        return comments.stream().map((item) -> commentsMapper.mapToDto(item)).collect(toList());
    }

    public List<CommentsDto> getCommentsByUsername(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName + " username was not found"));
        List<Comment> comments = commentRepository.findAllByUser(user);
        return comments.stream().map(commentsMapper::mapToDto).collect(toList());

    }
}
