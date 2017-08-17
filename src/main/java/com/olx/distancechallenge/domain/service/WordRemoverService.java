package com.olx.distancechallenge.domain.service;

import com.olx.distancechallenge.domain.model.Word;
import com.olx.distancechallenge.domain.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordRemoverService {

    private final WordGetterService wordGetterService;
    private final WordRepository wordRepository;

    @Autowired
    public WordRemoverService(WordGetterService wordGetterService, WordRepository wordRepository) {
        this.wordGetterService = wordGetterService;
        this.wordRepository = wordRepository;
    }

    public void remove(String word) {
        Word w = wordGetterService.get(word);
        wordRepository.remove(w);
    }

}
