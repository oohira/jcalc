package com.github.oohira.jcalc.token;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.github.oohira.jcalc.token.TokenType.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link Tokenizer} のテストクラス.
 */
class TokenizerTest {

    @Test
    void iterator() {
        Tokenizer tokenizer = new Tokenizer("1 \"2\"");

        Token token;
        Iterator<Token> it = tokenizer.iterator();

        assertTrue(it.hasNext());
        token = it.next();
        assertEquals(NUMBER, token.getType());
        assertEquals("1", token.getText());

        assertTrue(it.hasNext());
        token = it.next();
        assertEquals(STRING, token.getType());
        assertEquals("2", token.getText());

        assertFalse(it.hasNext());
        try {
            it.next();
            fail("should throw a NoSuchElementException");
        } catch (NoSuchElementException e) {
            assertTrue(true);
        }
    }

    @Test
    void peek() {
        Tokenizer tokenizer = new Tokenizer("1 \"2\"");

        assertEquals("1", tokenizer.peek().getText());
        assertEquals("1", tokenizer.peek().getText());

        tokenizer.next();
        assertEquals("2", tokenizer.peek().getText());
        assertEquals("2", tokenizer.peek().getText());

        tokenizer.next();
        try {
            tokenizer.peek();
            fail("should throw a NoSuchElementException");
        } catch (NoSuchElementException e) {
            assertTrue(true);
        }
    }

    @Test
    void scan() {
        Tokenizer tokenizer = new Tokenizer("(10 + 2) * (5-3/3)");
        Iterator<Token> it = tokenizer.iterator();

        assertTrue(it.hasNext());
        assertEquals(new Token(PAREN_LEFT, "("), it.next());
        assertEquals(new Token(NUMBER, "10"), it.next());
        assertEquals(new Token(OP_PLUS, "+"), it.next());
        assertEquals(new Token(NUMBER, "2"), it.next());
        assertEquals(new Token(PAREN_RIGHT, ")"), it.next());
        assertEquals(new Token(OP_MULTI, "*"), it.next());
        assertEquals(new Token(PAREN_LEFT, "("), it.next());
        assertEquals(new Token(NUMBER, "5"), it.next());
        assertEquals(new Token(OP_MINUS, "-"), it.next());
        assertEquals(new Token(NUMBER, "3"), it.next());
        assertEquals(new Token(OP_DIV, "/"), it.next());
        assertEquals(new Token(NUMBER, "3"), it.next());
        assertEquals(new Token(PAREN_RIGHT, ")"), it.next());
        assertFalse(it.hasNext());
    }
}
