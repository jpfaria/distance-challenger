package com.olx.distancechallenge.view.web.rest.controller;

import com.olx.distancechallenge.view.web.rest.handler.WordCreatorHandler;
import com.olx.distancechallenge.view.web.rest.handler.WordFinderHandler;
import com.olx.distancechallenge.view.web.rest.model.request.WordRequest;
import com.olx.distancechallenge.view.web.rest.model.response.WordResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.olx.distancechallenge.view.web.rest.util.AllowHeaderUtil.allows;

@RestController
@RequestMapping(path = "/")
public class WordCollectionController {

    private final WordCreatorHandler wordCreatorHandler;
    private final WordFinderHandler wordFinderHandler;

    @Autowired
    public WordCollectionController(WordCreatorHandler wordCreatorHandler, WordFinderHandler wordFinderHandler) {
        this.wordCreatorHandler = wordCreatorHandler;
        this.wordFinderHandler = wordFinderHandler;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> create(@RequestBody WordRequest request) {
        return wordCreatorHandler.create(request);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<WordResponse>> find(@RequestParam(required = false) String keyword, @RequestParam(defaultValue = "3", required = false) Integer threshold) {
        return wordFinderHandler.find(keyword, threshold);
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity options() {
        return allows(HttpMethod.POST, HttpMethod.GET, HttpMethod.OPTIONS);
    }

}
