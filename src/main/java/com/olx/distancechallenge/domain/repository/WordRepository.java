package com.olx.distancechallenge.domain.repository;

import com.olx.distancechallenge.domain.model.Word;

import java.util.List;

public interface WordRepository {

    void create(Word word);
    void remove(Word word);
    Word get(String word);
    List<Word> findAll();

}
