package com.olx.distancechallenge.view.web.rest.handler;

import com.olx.distancechallenge.core.component.OrikaMapperComponent;
import com.olx.distancechallenge.domain.model.Word;
import com.olx.distancechallenge.domain.service.WordCreatorService;
import com.olx.distancechallenge.view.web.rest.model.request.WordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class WordCreatorHandler {

    private final OrikaMapperComponent mapper;
    private final WordCreatorService wordCreatorService;

    @Autowired
    public WordCreatorHandler(OrikaMapperComponent mapper, WordCreatorService wordCreatorService) {
        this.mapper = mapper;
        this.wordCreatorService = wordCreatorService;
    }

    public ResponseEntity<Void> create(WordRequest request) {
        Word word = mapper.map(request, Word.class);
        wordCreatorService.create(word);
        return ResponseEntity.created(URI.create("")).build();
    }

}
