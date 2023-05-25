package com.example.reddit.repository;

import com.example.reddit.model.Post;
import com.example.reddit.model.User;
import com.example.reddit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);

    @Modifying
    @Query("DELETE FROM Vote v WHERE v.post.id = :postId")
    void deleteAllByPost(@Param("postId")Long postId);
}
