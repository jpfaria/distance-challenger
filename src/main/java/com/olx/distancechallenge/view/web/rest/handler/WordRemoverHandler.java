package com.olx.distancechallenge.view.web.rest.handler;

import com.olx.distancechallenge.domain.service.WordRemoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WordRemoverHandler {

    private final WordRemoverService wordRemoverService;

    @Autowired
    public WordRemoverHandler(WordRemoverService wordRemoverService) {
        this.wordRemoverService = wordRemoverService;
    }

    public ResponseEntity remove(String word) {
        wordRemoverService.remove(word);
        return ResponseEntity.noContent().build();
    }

}
