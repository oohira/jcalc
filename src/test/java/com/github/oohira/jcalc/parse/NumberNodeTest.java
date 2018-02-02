package com.github.oohira.jcalc.parse;

import com.github.oohira.jcalc.token.Token;
import org.junit.Test;

import java.math.BigDecimal;

import static com.github.oohira.jcalc.token.TokenType.NUMBER;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * {@link NumberNode} のテストクラス.
 */
public class NumberNodeTest {

    @Test
    public void zero() {
        Token token = new Token(NUMBER, "0");
        NumberNode n = new NumberNode(token);
        assertThat(n.eval(), is(BigDecimal.ZERO));
    }

    @Test
    public void one() {
        Token token = new Token(NUMBER, "1.0");
        NumberNode n = new NumberNode(token);
        assertThat(n.eval(), is(new BigDecimal("1.0")));
    }

    @Test
    public void ten() {
        Token token = new Token(NUMBER, "10");
        NumberNode n = new NumberNode(token);
        assertThat(n.eval(), is(BigDecimal.TEN));
    }

    @Test
    public void pi() {
        Token token = new Token(NUMBER, "3.14159");
        NumberNode n = new NumberNode(token);
        assertThat(n.eval(), is(new BigDecimal("3.14159")));
    }
}
