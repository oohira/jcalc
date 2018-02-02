package com.github.oohira.jcalc.parse;

import com.github.oohira.jcalc.token.Token;
import org.junit.Test;

import static com.github.oohira.jcalc.token.TokenType.STRING;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * {@link StringNode} のテストクラス.
 */
public class StringNodeTest {

    @Test
    public void empty() {
        Token token = new Token(STRING, "");
        StringNode n = new StringNode(token);
        assertThat(n.eval(), is(""));
    }

    @Test
    public void word() {
        Token token = new Token(STRING, "Hello, World!");
        StringNode n = new StringNode(token);
        assertThat(n.eval(), is("Hello, World!"));
    }

    @Test
    public void quote() {
        Token token = new Token(STRING, "\"''\"");
        StringNode n = new StringNode(token);
        assertThat(n.eval(), is("\"''\""));
    }
}
