package com.example.reddit.service;

import com.example.reddit.dto.VoteDto;
import com.example.reddit.exception.PostNotFoundException;
import com.example.reddit.exception.SpringRedditException;
import com.example.reddit.model.Post;
import com.example.reddit.model.Vote;
import com.example.reddit.repository.PostRepository;
import com.example.reddit.repository.VoteRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.reddit.model.VoteType.UPVOTE;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class VoteService {
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(voteDto.getPostId() + " was not found"));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if((voteByPostAndUser.isPresent()) &&
                !voteByPostAndUser.get().getVoteType()
                        .equals(voteDto.getVoteType())){
            if( UPVOTE.equals(voteDto.getVoteType())) {
                post.setVoteCount(post.getVoteCount() + 2);
            } else {
                post.setVoteCount(post.getVoteCount() - 2);
            }
        }
        if((voteByPostAndUser.isPresent()) &&
            voteByPostAndUser.get().getVoteType()
                .equals(voteDto.getVoteType())){
            throw new SpringRedditException("You have already " + voteDto.getVoteType() + "'d for this post");
        }
        if((!voteByPostAndUser.isPresent())) {
            if( UPVOTE.equals(voteDto.getVoteType())) {
                post.setVoteCount(post.getVoteCount() + 1);
            } else {
                post.setVoteCount(post.getVoteCount() - 1);
            }
        }


        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }


    public Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();

    }

}
