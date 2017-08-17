package com.olx.distancechallenge.domain.util;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LevenshteinDistanceCalculatorTest {

    @Test
    public void whenCompareBananaAndAbacate_thenDistanceMustBe4() {
        assertEquals(4, LevenshteinDistanceCalculator.calculate("banana", "abacate"));
        assertEquals(4, LevenshteinDistance.getDefaultInstance().apply("banana", "abacate").intValue());
    }

    @Test
    public void whenCompareBananaAndAbacaxi_thenDistanceMustBe4() {
        assertEquals(4, LevenshteinDistanceCalculator.calculate("banana", "abacaxi"));
        assertEquals(4, LevenshteinDistance.getDefaultInstance().apply("banana", "abacaxi").intValue());
    }

    @Test
    public void whenCompareBananaAndBatata_thenDistanceMustBe2() {
        assertEquals(2, LevenshteinDistanceCalculator.calculate("banana", "batata"));
        assertEquals(2, LevenshteinDistance.getDefaultInstance().apply("banana", "batata").intValue());
    }

    @Test
    public void whenCompareBananaAndBanana_thenDistanceMustBe0() {
        assertEquals(0, LevenshteinDistanceCalculator.calculate("banana", "banana"));
        assertEquals(0, LevenshteinDistance.getDefaultInstance().apply("banana", "banana").intValue());
    }

    @Test
    public void whenCompareAcaiAndAbacaxi_thenDistanceMustBe4() {
        assertEquals(4, LevenshteinDistanceCalculator.calculate("abacaxi", "açai"));
        assertEquals(4, LevenshteinDistance.getDefaultInstance().apply("abacaxi", "açai").intValue());
    }
}