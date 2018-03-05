package com.github.oohira.jcalc.parse;

import com.github.oohira.jcalc.token.Token;
import org.junit.Test;

import java.math.BigDecimal;

import static com.github.oohira.jcalc.token.TokenType.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * {@link BinaryOpNode} のテストクラス.
 */
public class BinaryOpNodeTest {

    @Test
    public void plus() {
        BinaryOpNode n = new BinaryOpNode(
                OP_PLUS,
                new NumberNode(new Token(NUMBER, "0.2")),
                new NumberNode(new Token(NUMBER, "0.1")));

        assertThat(n.getOperator(), is(OP_PLUS));

        NumberNode left = (NumberNode) n.getLeftOperand();
        assertThat(left.getValue(), is(new BigDecimal("0.2")));

        NumberNode right = (NumberNode) n.getRightOperand();
        assertThat(right.getValue(), is(new BigDecimal("0.1")));
    }
}
