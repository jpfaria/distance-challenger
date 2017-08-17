package com.olx.distancechallenge.view.web.rest.controller;

import com.olx.distancechallenge.view.web.rest.handler.WordRemoverHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.olx.distancechallenge.view.web.rest.util.AllowHeaderUtil.allows;

@RestController
@RequestMapping(path = "/{word}")
public class WordMemberController {

    private final WordRemoverHandler wordRemoverHandler;

    @Autowired
    public WordMemberController(WordRemoverHandler wordRemoverHandler) {
        this.wordRemoverHandler = wordRemoverHandler;
    }

    @DeleteMapping
    public ResponseEntity<Void> remove(@PathVariable String word) {
        return wordRemoverHandler.remove(word);
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity options() {
        return allows(HttpMethod.DELETE, HttpMethod.OPTIONS);
    }

}
