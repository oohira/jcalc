package com.github.oohira.jcalc.parse;

import com.github.oohira.jcalc.token.Token;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.github.oohira.jcalc.token.TokenType.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link BinaryOpNode} のテストクラス.
 */
class BinaryOpNodeTest {

    @Test
    void plus() {
        BinaryOpNode n = new BinaryOpNode(
                OP_PLUS,
                new NumberNode(new Token(NUMBER, "0.2")),
                new NumberNode(new Token(NUMBER, "0.1")));

        assertEquals(OP_PLUS, n.getOperator());

        NumberNode left = (NumberNode) n.getLeftOperand();
        assertEquals(new BigDecimal("0.2"), left.getValue());

        NumberNode right = (NumberNode) n.getRightOperand();
        assertEquals(new BigDecimal("0.1"), right.getValue());
    }
}
