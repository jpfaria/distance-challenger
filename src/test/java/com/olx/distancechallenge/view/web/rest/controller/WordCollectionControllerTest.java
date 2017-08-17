package com.olx.distancechallenge.view.web.rest.controller;

import com.olx.distancechallenge.Application;
import com.olx.distancechallenge.domain.model.Word;
import com.olx.distancechallenge.domain.repository.WordRepository;
import com.olx.distancechallenge.view.web.rest.model.request.WordRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static com.olx.distancechallenge.util.TestUtil.convertObjectToJsonBytes;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = WordCollectionController.class)
@ContextConfiguration(classes = Application.class)
@ComponentScan({"com.olx"})
public class WordCollectionControllerTest {

    @Autowired
    protected MockMvc mvc;

    @MockBean
    private WordRepository wordRepository;

    @Test
    public void whenAddWordAndWordNotRegistered_thenReturnCreated() throws Exception {

        given(this.wordRepository.get(any()))
                .willReturn(null);

        WordRequest request = WordRequest.builder().name("word").build();

        this.mvc.perform(
                post("/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(convertObjectToJsonBytes(request))
        )
                .andExpect(status().isCreated());
    }

    @Test
    public void whenAddWordAndWordHasRegistered_thenReturnConflict() throws Exception {

        given(this.wordRepository.get(any()))
                .willReturn(Word.builder().name("word").build());

        WordRequest request = WordRequest.builder().name("word").build();

        this.mvc.perform(
                post("/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(convertObjectToJsonBytes(request))
        )
                .andExpect(status().isConflict());
    }

    @Test
    public void whenSearchingForNearbyWordsAndNotFound_thenReturnEmptyList() throws Exception {

        given(this.wordRepository.findAll())
                .willReturn(Arrays.asList(Word.builder().name("banana").build(), Word.builder().name("cebola").build()));

        WordRequest request = WordRequest.builder().name("word").build();

        this.mvc.perform(get("/")
                .param("keyword", "repolho")
                .param("threshold", "1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    public void whenSearchingForNearbyWordsAndFound_thenReturnList() throws Exception {

        given(this.wordRepository.findAll())
                .willReturn(Arrays.asList(Word.builder().name("banana").build(), Word.builder().name("cebola").build()));

        WordRequest request = WordRequest.builder().name("word").build();

        this.mvc.perform(get("/")
                .param("keyword", "cebola")
                .param("threshold", "1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

    }

}