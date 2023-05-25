package com.example.reddit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subreddit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    private String name;
    private String description;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subreddit")
    private List<Post> posts;
    private Instant createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private String bgUrl;
    private String iconUrl;
}
