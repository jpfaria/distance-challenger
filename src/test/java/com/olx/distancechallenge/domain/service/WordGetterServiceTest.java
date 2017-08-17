package com.olx.distancechallenge.domain.service;

import com.olx.distancechallenge.domain.exception.WordNotFoundException;
import com.olx.distancechallenge.domain.model.Word;
import com.olx.distancechallenge.domain.repository.WordRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WordGetterService.class)
public class WordGetterServiceTest {

    @MockBean
    private WordRepository wordRepository;

    @Autowired
    private WordGetterService wordGetterService;

    @Test
    public void whenRetrieveExistsWord_thenIsSuccess() {
        when(wordRepository.get(any())).thenReturn(Word.builder().name("word").build());
        wordGetterService.get("word");
    }

    @Test(expected = WordNotFoundException.class)
    public void whenCreateExistsWord_thenThrowException() {
        when(wordRepository.get(any())).thenReturn(null);
        wordGetterService.get("word");
    }

}