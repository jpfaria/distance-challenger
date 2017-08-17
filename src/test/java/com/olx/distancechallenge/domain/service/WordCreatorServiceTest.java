package com.olx.distancechallenge.domain.service;

import com.olx.distancechallenge.domain.exception.WordConflictException;
import com.olx.distancechallenge.domain.model.Word;
import com.olx.distancechallenge.domain.repository.WordRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WordCreatorService.class)
public class WordCreatorServiceTest {

    @MockBean
    private WordRepository wordRepository;

    @Autowired
    private WordCreatorService wordCreatorService;

    @Test
    public void whenCreateNewWord_thenIsSuccess() {
        when(wordRepository.get(any())).thenReturn(null);
        wordCreatorService.create(Word.builder().name("word").build());
    }

    @Test(expected = WordConflictException.class)
    public void whenCreateExistsWord_thenThrowException() {
        when(wordRepository.get(any())).thenReturn(Word.builder().name("word").build());
        wordCreatorService.create(Word.builder().name("word").build());
    }


}