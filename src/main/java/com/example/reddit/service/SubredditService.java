package com.example.reddit.service;

import com.example.reddit.dto.SubredditDto;
import com.example.reddit.exception.SpringRedditException;
import com.example.reddit.exception.SubredditNotFoundException;
import com.example.reddit.mapper.SubredditMapper;
import com.example.reddit.model.Subreddit;
import com.example.reddit.repository.SubredditRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(save.getId());
        return subredditDto;
    }



    public List<SubredditDto> getAll() {
        return subredditRepository.findAll().stream().map(subredditMapper::mapSubredditToDto).collect(Collectors.toList());
    }

    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SubredditNotFoundException("Subreddit with " + id + " not found"));
        return subredditMapper.mapSubredditToDto(subreddit);
    }

    public SubredditDto getSubredditByName(String subredditName) {
        Subreddit subreddit = subredditRepository.findByName(subredditName)
                .orElseThrow(() -> new SubredditNotFoundException("Subreddit with " + subredditName + " not found"));

        return subredditMapper.mapSubredditToDto(subreddit);
    }
}
