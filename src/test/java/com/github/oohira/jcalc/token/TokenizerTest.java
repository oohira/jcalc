package com.github.oohira.jcalc.token;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.github.oohira.jcalc.token.TokenType.*;
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
        Tokenizer tokenizer = new Tokenizer("1 \"2\"");

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
        Tokenizer tokenizer = new Tokenizer("1 \"2\"");

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

    @Test
    public void scan() {
        Tokenizer tokenizer = new Tokenizer("(10 + 2) * (5-3/3)");
        Iterator<Token> it = tokenizer.iterator();

        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(new Token(PAREN_LEFT, "(")));
        assertThat(it.next(), is(new Token(NUMBER, "10")));
        assertThat(it.next(), is(new Token(OP_PLUS, "+")));
        assertThat(it.next(), is(new Token(NUMBER, "2")));
        assertThat(it.next(), is(new Token(PAREN_RIGHT, ")")));
        assertThat(it.next(), is(new Token(OP_MULTI, "*")));
        assertThat(it.next(), is(new Token(PAREN_LEFT, "(")));
        assertThat(it.next(), is(new Token(NUMBER, "5")));
        assertThat(it.next(), is(new Token(OP_MINUS, "-")));
        assertThat(it.next(), is(new Token(NUMBER, "3")));
        assertThat(it.next(), is(new Token(OP_DIV, "/")));
        assertThat(it.next(), is(new Token(NUMBER, "3")));
        assertThat(it.next(), is(new Token(PAREN_RIGHT, ")")));
        assertThat(it.hasNext(), is(false));
    }
}
