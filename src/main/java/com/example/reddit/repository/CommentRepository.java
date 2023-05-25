package com.example.reddit.repository;

import com.example.reddit.model.Comment;
import com.example.reddit.model.Post;
import com.example.reddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);

    List<Comment> findAllByUser(User user);

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.post.id = :postId")
    void deleteAllByPost(@Param("postId")Long postId);
}
