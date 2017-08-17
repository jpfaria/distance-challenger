package com.olx.distancechallenge.domain.service;

import com.olx.distancechallenge.domain.exception.WordNotFoundException;
import com.olx.distancechallenge.domain.model.Word;
import com.olx.distancechallenge.domain.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordGetterService {

    private static final String NOT_FOUND_MESSAGE = "The word entered was not found";

    private final WordRepository wordRepository;

    @Autowired
    public WordGetterService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public Word get(String word) {

        Word w = wordRepository.get(word);

        if (w == null) throw new WordNotFoundException(NOT_FOUND_MESSAGE);

        return w;
    }

}
