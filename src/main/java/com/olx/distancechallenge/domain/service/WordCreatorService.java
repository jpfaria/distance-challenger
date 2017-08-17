package com.olx.distancechallenge.domain.service;

import com.olx.distancechallenge.domain.exception.WordConflictException;
import com.olx.distancechallenge.domain.model.Word;
import com.olx.distancechallenge.domain.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordCreatorService {

    private static final String CONFLICT_MESSAGE = "The informed word has already been registered previously";

    private final WordRepository wordRepository;

    @Autowired
    public WordCreatorService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public void create(Word word) {

        Word persisted = wordRepository.get(word.getName());

        if (persisted != null) throw new WordConflictException(CONFLICT_MESSAGE);

        wordRepository.create(word);
    }

}
