package com.github.oohira.jcalc.parse;

import com.github.oohira.jcalc.token.Tokenizer;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.github.oohira.jcalc.token.TokenType.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link Parser} のテストクラス.
 */
class ParserTest {

    @Test
    void parseNumber() {
        Tokenizer tokenizer = new Tokenizer("1");
        Parser parser = new Parser(tokenizer);

        NumberNode n = (NumberNode) parser.parse();
        assertEquals(new BigDecimal("1"), n.getValue());
    }

    @Test
    void parseString() {
        Tokenizer tokenizer = new Tokenizer("\"Hello\"");
        Parser parser = new Parser(tokenizer);

        StringNode n = (StringNode) parser.parse();
        assertEquals("Hello", n.getValue());
    }

    @Test
    void parseAddition() {
        Tokenizer tokenizer = new Tokenizer("0 + 1");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parse();
        assertEquals(OP_PLUS, n.getOperator());
        assertEquals(BigDecimal.ZERO, toNumber(n.getLeftOperand()));
        assertEquals(BigDecimal.ONE, toNumber(n.getRightOperand()));
    }

    @Test
    void parseAdditionSubtraction() {
        Tokenizer tokenizer = new Tokenizer("10 + 2 - 5");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parse();
        assertEquals(OP_MINUS, n.getOperator());
        assertTrue(n.getLeftOperand() instanceof BinaryOpNode);
        assertEquals(new BigDecimal("5"), toNumber(n.getRightOperand()));

        BinaryOpNode left = (BinaryOpNode) n.getLeftOperand();
        assertEquals(OP_PLUS, left.getOperator());
        assertEquals(new BigDecimal("10"), toNumber(left.getLeftOperand()));
        assertEquals(new BigDecimal("2"), toNumber(left.getRightOperand()));
    }

    @Test
    void parseMultiplication() {
        Tokenizer tokenizer = new Tokenizer("0 * 1");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parse();
        assertEquals(OP_MULTI, n.getOperator());
        assertEquals(BigDecimal.ZERO, toNumber(n.getLeftOperand()));
        assertEquals(BigDecimal.ONE, toNumber(n.getRightOperand()));
    }

    @Test
    void parseMultiplicationDivision() {
        Tokenizer tokenizer = new Tokenizer("10 * 2 / 5");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parse();
        assertEquals(OP_DIV, n.getOperator());
        assertTrue(n.getLeftOperand() instanceof BinaryOpNode);
        assertEquals(new BigDecimal("5"), toNumber(n.getRightOperand()));

        BinaryOpNode left = (BinaryOpNode) n.getLeftOperand();
        assertEquals(OP_MULTI, left.getOperator());
        assertEquals(new BigDecimal("10"), toNumber(left.getLeftOperand()));
        assertEquals(new BigDecimal("2"), toNumber(left.getRightOperand()));
    }

    @Test
    void parseAdditionMultiplication() {
        // 10 + 2 * 5 + 3
        // -> (+ (+ 10 (* 2 5)) 3)
        Tokenizer tokenizer = new Tokenizer("10 + 2 * 5 + 3");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parse();
        assertEquals(OP_PLUS, n.getOperator());
        assertTrue(n.getLeftOperand() instanceof BinaryOpNode);
        assertEquals(new BigDecimal("3"), toNumber(n.getRightOperand()));

        BinaryOpNode left = (BinaryOpNode) n.getLeftOperand();
        assertEquals(OP_PLUS, left.getOperator());
        assertEquals(new BigDecimal("10"), toNumber(left.getLeftOperand()));
        assertTrue(left.getRightOperand() instanceof BinaryOpNode);

        BinaryOpNode right = (BinaryOpNode) left.getRightOperand();
        assertEquals(OP_MULTI, right.getOperator());
        assertEquals(new BigDecimal("2"), toNumber(right.getLeftOperand()));
        assertEquals(new BigDecimal("5"), toNumber(right.getRightOperand()));
    }

    @Test
    void parseParentheses() {
        // (10 + 2) * (5 + 3)
        // -> (* (+ 10 2) (+ 5 3))
        Tokenizer tokenizer = new Tokenizer("(10 + 2) * (5 + 3)");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parse();
        assertEquals(OP_MULTI, n.getOperator());
        assertTrue(n.getLeftOperand() instanceof BinaryOpNode);
        assertTrue(n.getRightOperand() instanceof BinaryOpNode);

        BinaryOpNode left = (BinaryOpNode) n.getLeftOperand();
        assertEquals(OP_PLUS, left.getOperator());
        assertEquals(new BigDecimal("10"), toNumber(left.getLeftOperand()));
        assertEquals(new BigDecimal("2"), toNumber(left.getRightOperand()));

        BinaryOpNode right = (BinaryOpNode) n.getRightOperand();
        assertEquals(OP_PLUS, right.getOperator());
        assertEquals(new BigDecimal("5"), toNumber(right.getLeftOperand()));
        assertEquals(new BigDecimal("3"), toNumber(right.getRightOperand()));
    }

    @Test
    void parseEqualCondition() {
        Tokenizer tokenizer = new Tokenizer("0 == 1");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parseConditionalExpression(tokenizer);
        assertEquals(OP_EQUAL, n.getOperator());
        assertEquals(BigDecimal.ZERO, toNumber(n.getLeftOperand()));
        assertEquals(BigDecimal.ONE, toNumber(n.getRightOperand()));
    }

    @Test
    void parseNotEqualCondition() {
        Tokenizer tokenizer = new Tokenizer("0 != 1");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parseConditionalExpression(tokenizer);
        assertEquals(OP_NOT_EQUAL, n.getOperator());
        assertEquals(BigDecimal.ZERO, toNumber(n.getLeftOperand()));
        assertEquals(BigDecimal.ONE, toNumber(n.getRightOperand()));
    }

    @Test
    void parseLessCondition() {
        Tokenizer tokenizer = new Tokenizer("0 < 1");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parseConditionalExpression(tokenizer);
        assertEquals(OP_LESS, n.getOperator());
        assertEquals(BigDecimal.ZERO, toNumber(n.getLeftOperand()));
        assertEquals(BigDecimal.ONE, toNumber(n.getRightOperand()));
    }

    @Test
    void parseLessEqualCondition() {
        Tokenizer tokenizer = new Tokenizer("0 <= 1");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parseConditionalExpression(tokenizer);
        assertEquals(OP_LESS_EQUAL, n.getOperator());
        assertEquals(BigDecimal.ZERO, toNumber(n.getLeftOperand()));
        assertEquals(BigDecimal.ONE, toNumber(n.getRightOperand()));
    }

    @Test
    void parseGreaterCondition() {
        Tokenizer tokenizer = new Tokenizer("0 > 1");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parseConditionalExpression(tokenizer);
        assertEquals(OP_GREATER, n.getOperator());
        assertEquals(BigDecimal.ZERO, toNumber(n.getLeftOperand()));
        assertEquals(BigDecimal.ONE, toNumber(n.getRightOperand()));
    }

    @Test
    void parseGreaterEqualCondition() {
        Tokenizer tokenizer = new Tokenizer("0 >= 1");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parseConditionalExpression(tokenizer);
        assertEquals(OP_GREATER_EQUAL, n.getOperator());
        assertEquals(BigDecimal.ZERO, toNumber(n.getLeftOperand()));
        assertEquals(BigDecimal.ONE, toNumber(n.getRightOperand()));
    }

    private BigDecimal toNumber(Node n) {
        return ((NumberNode) n).getValue();
    }
}
