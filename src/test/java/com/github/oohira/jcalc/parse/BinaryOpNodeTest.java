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
        assertThat(n.getLeftOperand(), instanceOf(NumberNode.class));
        assertThat(n.getRightOperand(), instanceOf(NumberNode.class));
        assertThat(n.eval(), is(new BigDecimal("0.3")));
    }

    @Test
    public void minus() {
        BinaryOpNode n = new BinaryOpNode(
                OP_MINUS,
                new NumberNode(new Token(NUMBER, "0.2")),
                new NumberNode(new Token(NUMBER, "0.1")));

        assertThat(n.getOperator(), is(OP_MINUS));
        assertThat(n.getLeftOperand(), instanceOf(NumberNode.class));
        assertThat(n.getRightOperand(), instanceOf(NumberNode.class));
        assertThat(n.eval(), is(new BigDecimal("0.1")));
    }

    @Test
    public void mulitiply() {
        BinaryOpNode n = new BinaryOpNode(
                OP_MULTI,
                new NumberNode(new Token(NUMBER, "0.2")),
                new NumberNode(new Token(NUMBER, "0.1")));

        assertThat(n.getOperator(), is(OP_MULTI));
        assertThat(n.getLeftOperand(), instanceOf(NumberNode.class));
        assertThat(n.getRightOperand(), instanceOf(NumberNode.class));
        assertThat(n.eval(), is(new BigDecimal("0.02")));
    }

    @Test
    public void division() {
        BinaryOpNode n = new BinaryOpNode(
                OP_DIV,
                new NumberNode(new Token(NUMBER, "0.2")),
                new NumberNode(new Token(NUMBER, "0.1")));

        assertThat(n.getOperator(), is(OP_DIV));
        assertThat(n.getLeftOperand(), instanceOf(NumberNode.class));
        assertThat(n.getRightOperand(), instanceOf(NumberNode.class));
        assertThat(n.eval(), is(new BigDecimal("2")));
    }

    @Test
    public void composite() {
        BinaryOpNode n = new BinaryOpNode(
                OP_PLUS,
                new NumberNode(new Token(NUMBER, "1")),
                new BinaryOpNode(
                        OP_MULTI,
                        new NumberNode(new Token(NUMBER, "2")),
                        new NumberNode(new Token(NUMBER, "3"))));

        assertThat(n.getOperator(), is(OP_PLUS));
        assertThat(n.getLeftOperand(), instanceOf(NumberNode.class));
        assertThat(n.getRightOperand(), instanceOf(BinaryOpNode.class));

        BinaryOpNode right = (BinaryOpNode) n.getRightOperand();
        assertThat(right.getOperator(), is(OP_MULTI));
        assertThat(right.getLeftOperand(), instanceOf(NumberNode.class));
        assertThat(right.getRightOperand(), instanceOf(NumberNode.class));
        assertThat(right.eval(), is(new BigDecimal("6")));
        assertThat(n.eval(), is(new BigDecimal("7")));
    }
}
