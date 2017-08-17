package com.olx.distancechallenge.view.web.rest.controller;

import com.olx.distancechallenge.Application;
import com.olx.distancechallenge.domain.model.Word;
import com.olx.distancechallenge.domain.repository.WordRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = WordMemberController.class)
@ContextConfiguration(classes = Application.class)
@ComponentScan({"com.olx"})
public class WordMemberControllerTest {

    @Autowired
    protected MockMvc mvc;

    @MockBean
    private WordRepository wordRepository;

    @Test
    public void whenRemoveWordAndWordNotRegistered_thenReturnNotFound() throws Exception {

        given(this.wordRepository.get(any()))
                .willReturn(null);

        this.mvc.perform(delete("/{word}", "word"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenRemoveWordAndWordHasRegistered_thenReturnNoContent() throws Exception {

        given(this.wordRepository.get(any()))
                .willReturn(Word.builder().name("word").build());

        this.mvc.perform(delete("/{word}", "word"))
                .andExpect(status().isNoContent());
    }

}