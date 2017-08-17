package com.olx.distancechallenge.persistence.memory.dao;

import com.olx.distancechallenge.domain.model.Word;
import com.olx.distancechallenge.domain.repository.WordRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Profile("word_memory")
public class WordMemoryDao implements WordRepository {

    private Set<String> words;

    @PostConstruct
    public void init() {
        words = new HashSet<>();
    }

    @Override
    public void create(Word word) {
        words.add(word.getName());
    }

    @Override
    public void remove(Word word) {
        words.remove(word.getName());
    }

    @Override
    public Word get(String word) {
        if (words.contains(word)) {
            return Word.builder().name(word).build();
        }
        return null;
    }

    @Override
    public List<Word> findAll() {
        return words.stream()
                .map(s -> Word.builder().name(s).build())
                .collect(Collectors.toList());
    }

}
