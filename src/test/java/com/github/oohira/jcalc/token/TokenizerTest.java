package com.github.oohira.jcalc.token;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.github.oohira.jcalc.token.TokenType.NUMBER;
import static com.github.oohira.jcalc.token.TokenType.STRING;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * {@link Tokenizer} のテストクラス.
 */
public class TokenizerTest {

    @Test
    public void iterator() {
        Tokenizer tokenizer = new Tokenizer(
                new Token(NUMBER, "1"), new Token(STRING, "2"));

        Token token;
        Iterator<Token> it = tokenizer.iterator();

        assertThat(it.hasNext(), is(true));
        token = it.next();
        assertThat(token.getType(), is(NUMBER));
        assertThat(token.getText(), is("1"));

        assertThat(it.hasNext(), is(true));
        token = it.next();
        assertThat(token.getType(), is(STRING));
        assertThat(token.getText(), is("2"));

        assertThat(it.hasNext(), is(false));
        try {
            it.next();
            fail("should throw a NoSuchElementException");
        } catch (NoSuchElementException e) {
            assertTrue(true);
        }
    }

    @Test
    public void peek() {
        Tokenizer tokenizer = new Tokenizer(
                new Token(NUMBER, "1"), new Token(STRING, "2"));

        assertThat(tokenizer.peek().getText(), is("1"));
        assertThat(tokenizer.peek().getText(), is("1"));

        tokenizer.next();
        assertThat(tokenizer.peek().getText(), is("2"));
        assertThat(tokenizer.peek().getText(), is("2"));

        tokenizer.next();
        try {
            tokenizer.peek();
            fail("should throw a NoSuchElementException");
        } catch (NoSuchElementException e) {
            assertTrue(true);
        }
    }
}
