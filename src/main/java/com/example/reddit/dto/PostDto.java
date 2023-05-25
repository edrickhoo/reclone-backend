package com.example.reddit.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long postId;
    private String subredditName;
    private String url;
    @Nullable
    private String description;
    @NotBlank(message = "Post Name cannot be empty or null")
    private String postName;
}
