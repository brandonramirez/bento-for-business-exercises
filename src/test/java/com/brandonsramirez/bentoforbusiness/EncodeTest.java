package com.brandonsramirez.bentoforbusiness;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EncodeTest {
    @Test
    public void encodesEmptyStringAsEmptyString() {
        assertEquals("", Encode.encode(""));
    }

    @Test
    public void charactersOccuringExactlyOnceInARowAreRepresentedAsThemselves() {
        assertEquals("dog", Encode.encode("dog"));
        assertEquals("cat", Encode.encode("cat"));
        assertEquals("alpha", Encode.encode("alpha"));
        assertEquals("beta", Encode.encode("beta"));
        assertEquals("abcd", Encode.encode("abcd"));
    }

    @Test
    public void charactersOccuringMultipleTimesInARowAreRepresentedWithTheirRunLength() {
        assertEquals("t7", Encode.encode("ttttttt"));
        assertEquals("a2b3c4d5", Encode.encode("aabbbccccddddd"));
    }

    @Test
    public void charactersOccuringMultipleTimesInARowIntermixedWithSingleCharacters() {
        assertEquals("gam2a", Encode.encode("gamma"));
        assertEquals("abca3b3", Encode.encode("abcaaabbb"));
        assertEquals("a3ba4c2a4ba", Encode.encode("aaabaaaaccaaaaba"));
    }
}
