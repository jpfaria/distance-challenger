package com.olx.distancechallenge.persistence.mongo.dao;

import com.olx.distancechallenge.core.component.OrikaMapperComponent;
import com.olx.distancechallenge.domain.model.Word;
import com.olx.distancechallenge.domain.repository.WordRepository;
import com.olx.distancechallenge.persistence.mongo.document.WordDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@Profile("word_mongo")
public class WordMongoDao implements WordRepository {

    private final MongoTemplate mongoTemplate;
    private final OrikaMapperComponent mapper;

    @Autowired
    public WordMongoDao(MongoTemplate mongoTemplate, OrikaMapperComponent mapper) {
        this.mongoTemplate = mongoTemplate;
        this.mapper = mapper;
    }

    @Override
    public void create(Word word) {
        WordDocument document = mapper.map(word, WordDocument.class);
        mongoTemplate.save(document);
    }

    @Override
    public void remove(Word word) {
        WordDocument document = mongoTemplate.findOne(new Query(where("name").is(word.getName())), WordDocument.class);
        if (document != null) mongoTemplate.remove(document);
    }

    @Override
    public Word get(String word) {
        Query query = new Query(where("name").is(word));
        WordDocument document = mongoTemplate.findOne(query, WordDocument.class);
        return mapper.map(document, Word.class);
    }

    @Override
    public List<Word> findAll() {
        List<WordDocument> documents = mongoTemplate.findAll(WordDocument.class);
        return mapper.mapAsList(documents, Word.class);
    }

}
