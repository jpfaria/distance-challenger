package com.olx.distancechallenge.view.web.rest.handler;

import com.olx.distancechallenge.core.component.OrikaMapperComponent;
import com.olx.distancechallenge.domain.model.Word;
import com.olx.distancechallenge.domain.repository.WordRepository;
import com.olx.distancechallenge.domain.service.WordProximityFinderService;
import com.olx.distancechallenge.view.web.rest.model.response.WordResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordFinderHandler {

    private final OrikaMapperComponent mapper;
    private final WordRepository wordRepository;
    private final WordProximityFinderService wordProximityFinderService;

    @Autowired
    public WordFinderHandler(OrikaMapperComponent mapper, WordRepository wordRepository, WordProximityFinderService wordProximityFinderService) {
        this.mapper = mapper;
        this.wordRepository = wordRepository;
        this.wordProximityFinderService = wordProximityFinderService;
    }

    public ResponseEntity<List<WordResponse>> find(String keyword, Integer threshold) {

        List<Word> words;

        if (keyword == null) {
            words = wordRepository.findAll();
        } else {
            words = wordProximityFinderService.find(keyword, threshold);
        }

        List<WordResponse> wordResponses = mapper.mapAsList(words, WordResponse.class);
        return ResponseEntity.ok().body(wordResponses);
    }

}
