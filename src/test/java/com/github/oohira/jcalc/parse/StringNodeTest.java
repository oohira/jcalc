package com.github.oohira.jcalc.parse;

import com.github.oohira.jcalc.token.Token;
import org.junit.jupiter.api.Test;

import static com.github.oohira.jcalc.token.TokenType.STRING;
import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link StringNode} のテストクラス.
 */
class StringNodeTest {

    @Test
    void empty() {
        Token token = new Token(STRING, "");
        StringNode n = new StringNode(token);
        assertEquals("", n.getValue());
    }

    @Test
    void word() {
        Token token = new Token(STRING, "Hello, World!");
        StringNode n = new StringNode(token);
        assertEquals("Hello, World!", n.getValue());
    }

    @Test
    void quote() {
        Token token = new Token(STRING, "\"''\"");
        StringNode n = new StringNode(token);
        assertEquals("\"''\"", n.getValue());
    }
}
