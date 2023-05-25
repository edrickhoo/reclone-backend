package com.example.reddit.dto;

import com.example.reddit.model.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.ProtocolFamily;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubredditDto {
    private Long id;
    @NotBlank(message = "Community name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    private Integer numberOfPosts;
    private String bgUrl;
    private String iconUrl;
    private Instant createdDate;
}
