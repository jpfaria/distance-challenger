package com.olx.distancechallenge.domain.service;

import com.olx.distancechallenge.domain.model.Word;
import com.olx.distancechallenge.domain.repository.WordRepository;
import com.olx.distancechallenge.domain.util.LevenshteinDistanceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordProximityFinderService {

    private final WordRepository wordRepository;

    @Autowired
    public WordProximityFinderService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public List<Word> find(String keyword, Integer threshold) {
        List<Word> words = wordRepository.findAll();
        return words.stream()
                .filter(word -> LevenshteinDistanceCalculator.calculate(keyword, word.getName()) <= threshold)
                .collect(Collectors.toList());
    }

}
