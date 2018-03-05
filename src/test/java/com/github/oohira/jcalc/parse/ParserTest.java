package com.github.oohira.jcalc.parse;

import com.github.oohira.jcalc.token.Tokenizer;
import org.junit.Test;

import java.math.BigDecimal;

import static com.github.oohira.jcalc.token.TokenType.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * {@link Parser} のテストクラス.
 */
public class ParserTest {

    @Test
    public void parseNumber() {
        Tokenizer tokenizer = new Tokenizer("1");
        Parser parser = new Parser(tokenizer);

        NumberNode n = (NumberNode) parser.parse();
        assertThat(n.getValue(), is(new BigDecimal("1")));
    }

    @Test
    public void parseString() {
        Tokenizer tokenizer = new Tokenizer("\"Hello\"");
        Parser parser = new Parser(tokenizer);

        StringNode n = (StringNode) parser.parse();
        assertThat(n.getValue(), is("Hello"));
    }

    @Test
    public void parseAddition() {
        Tokenizer tokenizer = new Tokenizer("0 + 1");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parse();
        assertThat(n.getOperator(), is(OP_PLUS));
        assertThat(toNumber(n.getLeftOperand()), is(BigDecimal.ZERO));
        assertThat(toNumber(n.getRightOperand()), is(BigDecimal.ONE));
    }

    @Test
    public void parseAdditionSubtraction() {
        Tokenizer tokenizer = new Tokenizer("10 + 2 - 5");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parse();
        assertThat(n.getOperator(), is(OP_MINUS));
        assertThat(n.getLeftOperand(), instanceOf(BinaryOpNode.class));
        assertThat(toNumber(n.getRightOperand()), is(new BigDecimal("5")));

        BinaryOpNode left = (BinaryOpNode) n.getLeftOperand();
        assertThat(left.getOperator(), is(OP_PLUS));
        assertThat(toNumber(left.getLeftOperand()), is(new BigDecimal("10")));
        assertThat(toNumber(left.getRightOperand()), is(new BigDecimal("2")));
    }

    @Test
    public void parseMultiplication() {
        Tokenizer tokenizer = new Tokenizer("0 * 1");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parse();
        assertThat(n.getOperator(), is(OP_MULTI));
        assertThat(toNumber(n.getLeftOperand()), is(BigDecimal.ZERO));
        assertThat(toNumber(n.getRightOperand()), is(BigDecimal.ONE));
    }

    @Test
    public void parseMultiplicationDivision() {
        Tokenizer tokenizer = new Tokenizer("10 * 2 / 5");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parse();
        assertThat(n.getOperator(), is(OP_DIV));
        assertThat(n.getLeftOperand(), instanceOf(BinaryOpNode.class));
        assertThat(toNumber(n.getRightOperand()), is(new BigDecimal("5")));

        BinaryOpNode left = (BinaryOpNode) n.getLeftOperand();
        assertThat(left.getOperator(), is(OP_MULTI));
        assertThat(toNumber(left.getLeftOperand()), is(new BigDecimal("10")));
        assertThat(toNumber(left.getRightOperand()), is(new BigDecimal("2")));
    }

    @Test
    public void parseAdditionMultiplication() {
        // 10 + 2 * 5 + 3
        // -> (+ (+ 10 (* 2 5)) 3)
        Tokenizer tokenizer = new Tokenizer("10 + 2 * 5 + 3");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parse();
        assertThat(n.getOperator(), is(OP_PLUS));
        assertThat(n.getLeftOperand(), instanceOf(BinaryOpNode.class));
        assertThat(toNumber(n.getRightOperand()), is(new BigDecimal("3")));

        BinaryOpNode left = (BinaryOpNode) n.getLeftOperand();
        assertThat(left.getOperator(), is(OP_PLUS));
        assertThat(toNumber(left.getLeftOperand()), is(new BigDecimal("10")));
        assertThat(left.getRightOperand(), instanceOf(BinaryOpNode.class));

        BinaryOpNode right = (BinaryOpNode) left.getRightOperand();
        assertThat(right.getOperator(), is(OP_MULTI));
        assertThat(toNumber(right.getLeftOperand()), is(new BigDecimal("2")));
        assertThat(toNumber(right.getRightOperand()), is(new BigDecimal("5")));
    }

    @Test
    public void parseParentheses() {
        // (10 + 2) * (5 + 3)
        // -> (* (+ 10 2) (+ 5 3))
        Tokenizer tokenizer = new Tokenizer("(10 + 2) * (5 + 3)");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parse();
        assertThat(n.getOperator(), is(OP_MULTI));
        assertThat(n.getLeftOperand(), instanceOf(BinaryOpNode.class));
        assertThat(n.getRightOperand(), instanceOf(BinaryOpNode.class));

        BinaryOpNode left = (BinaryOpNode) n.getLeftOperand();
        assertThat(left.getOperator(), is(OP_PLUS));
        assertThat(toNumber(left.getLeftOperand()), is(new BigDecimal("10")));
        assertThat(toNumber(left.getRightOperand()), is(new BigDecimal("2")));

        BinaryOpNode right = (BinaryOpNode) n.getRightOperand();
        assertThat(right.getOperator(), is(OP_PLUS));
        assertThat(toNumber(right.getLeftOperand()), is(new BigDecimal("5")));
        assertThat(toNumber(right.getRightOperand()), is(new BigDecimal("3")));
    }

    @Test
    public void parseEqualCondition() {
        Tokenizer tokenizer = new Tokenizer("0 == 1");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parseConditionalExpression(tokenizer);
        assertThat(n.getOperator(), is(OP_EQUAL));
        assertThat(toNumber(n.getLeftOperand()), is(BigDecimal.ZERO));
        assertThat(toNumber(n.getRightOperand()), is(BigDecimal.ONE));
    }

    @Test
    public void parseNotEqualCondition() {
        Tokenizer tokenizer = new Tokenizer("0 != 1");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parseConditionalExpression(tokenizer);
        assertThat(n.getOperator(), is(OP_NOT_EQUAL));
        assertThat(toNumber(n.getLeftOperand()), is(BigDecimal.ZERO));
        assertThat(toNumber(n.getRightOperand()), is(BigDecimal.ONE));
    }

    @Test
    public void parseLessCondition() {
        Tokenizer tokenizer = new Tokenizer("0 < 1");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parseConditionalExpression(tokenizer);
        assertThat(n.getOperator(), is(OP_LESS));
        assertThat(toNumber(n.getLeftOperand()), is(BigDecimal.ZERO));
        assertThat(toNumber(n.getRightOperand()), is(BigDecimal.ONE));
    }

    @Test
    public void parseLessEqualCondition() {
        Tokenizer tokenizer = new Tokenizer("0 <= 1");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parseConditionalExpression(tokenizer);
        assertThat(n.getOperator(), is(OP_LESS_EQUAL));
        assertThat(toNumber(n.getLeftOperand()), is(BigDecimal.ZERO));
        assertThat(toNumber(n.getRightOperand()), is(BigDecimal.ONE));
    }

    @Test
    public void parseGreaterCondition() {
        Tokenizer tokenizer = new Tokenizer("0 > 1");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parseConditionalExpression(tokenizer);
        assertThat(n.getOperator(), is(OP_GREATER));
        assertThat(toNumber(n.getLeftOperand()), is(BigDecimal.ZERO));
        assertThat(toNumber(n.getRightOperand()), is(BigDecimal.ONE));
    }

    @Test
    public void parseGreaterEqualCondition() {
        Tokenizer tokenizer = new Tokenizer("0 >= 1");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parseConditionalExpression(tokenizer);
        assertThat(n.getOperator(), is(OP_GREATER_EQUAL));
        assertThat(toNumber(n.getLeftOperand()), is(BigDecimal.ZERO));
        assertThat(toNumber(n.getRightOperand()), is(BigDecimal.ONE));
    }

    private BigDecimal toNumber(Node n) {
        return ((NumberNode) n).getValue();
    }
}
