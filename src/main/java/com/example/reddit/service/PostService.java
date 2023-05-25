package com.example.reddit.service;

import com.example.reddit.dto.PostDto;
import com.example.reddit.dto.PostResponse;
import com.example.reddit.exception.PostNotFoundException;
import com.example.reddit.exception.SpringRedditException;
import com.example.reddit.exception.SubredditNotFoundException;
import com.example.reddit.mapper.PostMapper;
import com.example.reddit.model.Post;
import com.example.reddit.model.Subreddit;
import com.example.reddit.model.User;
import com.example.reddit.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final AuthService authService;
    private final SubredditRepository subredditRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;
    private final CommentRepository commentRepository;

    public void save(PostDto postDto) {
        Subreddit subreddit = subredditRepository.findByName(postDto.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(postDto.getSubredditName() + " is not found"));
        User currentUser = authService.getCurrentUser();

        postRepository.save(postMapper.map(postDto,subreddit, currentUser));
    }

    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id + " id post was not found"));

        return postMapper.mapToResponse(post);
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map((item) -> postMapper.mapToResponse(item) )
                .collect(toList());
    }


    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId + "id subreddit was not found"));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);



        return posts.stream()
                .map((p) -> postMapper.mapToResponse(p))
                .collect(toList());
    }

    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " was not found"));
        List<Post> posts = postRepository.findAllByUser(user);

        return posts.stream().map(postMapper::mapToResponse).collect(toList());
    }

    public List<PostResponse> getPostsBySubredditName(String subredditName) {
        Subreddit subreddit = subredditRepository.findByName(subredditName)
                .orElseThrow(() -> new SubredditNotFoundException(subredditName + " subreddit name was not found"));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);



        return posts.stream()
                .map((p) -> postMapper.mapToResponse(p))
                .collect(toList());
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id + " id post was not found"));
        if(post.getUser().getUsername() != authService.getCurrentUser().getUsername()) {
            throw new SpringRedditException("Unauthorized");
        }
        voteRepository.deleteAllByPost(id);
        commentRepository.deleteAllByPost(id);
        postRepository.deleteById(id);
    }
}
