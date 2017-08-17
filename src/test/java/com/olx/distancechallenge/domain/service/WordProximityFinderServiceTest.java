package com.olx.distancechallenge.domain.service;

import com.olx.distancechallenge.domain.model.Word;
import com.olx.distancechallenge.domain.repository.WordRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WordProximityFinderService.class)
public class WordProximityFinderServiceTest {

    @MockBean
    private WordRepository wordRepository;

    @Autowired
    private WordProximityFinderService wordProximityFinderService;

    @Test
    public void whenFindByProximityWord_thenMatch() {
        when(wordRepository.findAll()).thenReturn(Arrays.asList(Word.builder().name("banana").build(), Word.builder().name("cebola").build()));
        List<Word> words = wordProximityFinderService.find("batata", 2);

        assertEquals(1, words.size());
    }

    @Test
    public void whenFindByNotProximityWord_thenReturnNothing() {
        when(wordRepository.findAll()).thenReturn(Arrays.asList(Word.builder().name("banana").build(), Word.builder().name("cebola").build()));
        List<Word> words = wordProximityFinderService.find("repolho", 2);

        assertEquals(0, words.size());
    }

}