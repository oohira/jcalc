package com.github.oohira.jcalc.parse;

import com.github.oohira.jcalc.token.Token;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.github.oohira.jcalc.token.TokenType.NUMBER;
import static org.junit.jupiter.api.Assertions.*;


/**
 * {@link NumberNode} のテストクラス.
 */
class NumberNodeTest {

    @Test
    void zero() {
        Token token = new Token(NUMBER, "0");
        NumberNode n = new NumberNode(token);
        assertEquals(BigDecimal.ZERO, n.getValue());
    }

    @Test
    void one() {
        Token token = new Token(NUMBER, "1.0");
        NumberNode n = new NumberNode(token);
        assertEquals(new BigDecimal("1.0"), n.getValue());
    }

    @Test
    void pi() {
        Token token = new Token(NUMBER, "3.14159");
        NumberNode n = new NumberNode(token);
        assertEquals(new BigDecimal("3.14159"), n.getValue());
    }
}
