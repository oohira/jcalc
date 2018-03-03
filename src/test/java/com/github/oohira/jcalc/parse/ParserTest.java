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
        assertThat(n.eval(), is(new BigDecimal("1")));
    }

    @Test
    public void parseString() {
        Tokenizer tokenizer = new Tokenizer("\"Hello\"");
        Parser parser = new Parser(tokenizer);

        StringNode n = (StringNode) parser.parse();
        assertThat(n.eval(), is("Hello"));
    }

    @Test
    public void parseAddition() {
        Tokenizer tokenizer = new Tokenizer("1 + 2");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parse();
        assertThat(n.getOperator(), is(OP_PLUS));
        assertThat(n.getLeftOperand(), instanceOf(NumberNode.class));
        assertThat(n.getLeftOperand().eval(), is(new BigDecimal("1")));
        assertThat(n.getRightOperand(), instanceOf(NumberNode.class));
        assertThat(n.getRightOperand().eval(), is(new BigDecimal("2")));
        assertThat(n.eval(), is(new BigDecimal("3")));
    }

    @Test
    public void parseAdditionSubtraction() {
        Tokenizer tokenizer = new Tokenizer("10 + 2 - 5");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parse();
        assertThat(n.getOperator(), is(OP_MINUS));
        assertThat(n.getLeftOperand(), instanceOf(BinaryOpNode.class));
        assertThat(n.getRightOperand(), instanceOf(NumberNode.class));
        assertThat(n.eval(), is(new BigDecimal("7")));

        BinaryOpNode left = (BinaryOpNode) n.getLeftOperand();
        assertThat(left.getOperator(), is(OP_PLUS));
        assertThat(left.getLeftOperand(), instanceOf(NumberNode.class));
        assertThat(left.getRightOperand(), instanceOf(NumberNode.class));
        assertThat(left.eval(), is(new BigDecimal("12")));
    }

    @Test
    public void parseMultiplication() {
        Tokenizer tokenizer = new Tokenizer("1 * 2");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parse();
        assertThat(n.getOperator(), is(OP_MULTI));
        assertThat(n.getLeftOperand(), instanceOf(NumberNode.class));
        assertThat(n.getLeftOperand().eval(), is(new BigDecimal("1")));
        assertThat(n.getRightOperand(), instanceOf(NumberNode.class));
        assertThat(n.getRightOperand().eval(), is(new BigDecimal("2")));
        assertThat(n.eval(), is(new BigDecimal("2")));
    }

    @Test
    public void parseMultiplicationDivision() {
        Tokenizer tokenizer = new Tokenizer("10 * 2 / 5");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parse();
        assertThat(n.getOperator(), is(OP_DIV));
        assertThat(n.getLeftOperand(), instanceOf(BinaryOpNode.class));
        assertThat(n.getRightOperand(), instanceOf(NumberNode.class));
        assertThat(n.eval(), is(new BigDecimal("4")));

        BinaryOpNode left = (BinaryOpNode) n.getLeftOperand();
        assertThat(left.getOperator(), is(OP_MULTI));
        assertThat(left.getLeftOperand(), instanceOf(NumberNode.class));
        assertThat(left.getRightOperand(), instanceOf(NumberNode.class));
        assertThat(left.eval(), is(new BigDecimal("20")));
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
        assertThat(n.getRightOperand(), instanceOf(NumberNode.class));
        assertThat(n.eval(), is(new BigDecimal("23")));

        BinaryOpNode left = (BinaryOpNode) n.getLeftOperand();
        assertThat(left.getOperator(), is(OP_PLUS));
        assertThat(left.getLeftOperand(), instanceOf(NumberNode.class));
        assertThat(left.getRightOperand(), instanceOf(BinaryOpNode.class));
        assertThat(left.eval(), is(new BigDecimal("20")));

        BinaryOpNode right = (BinaryOpNode) left.getRightOperand();
        assertThat(right.getOperator(), is(OP_MULTI));
        assertThat(right.getLeftOperand(), instanceOf(NumberNode.class));
        assertThat(right.getRightOperand(), instanceOf(NumberNode.class));
        assertThat(right.eval(), is(new BigDecimal("10")));
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
        assertThat(n.eval(), is(new BigDecimal("96")));

        BinaryOpNode left = (BinaryOpNode) n.getLeftOperand();
        assertThat(left.getOperator(), is(OP_PLUS));
        assertThat(left.getLeftOperand(), instanceOf(NumberNode.class));
        assertThat(left.getRightOperand(), instanceOf(NumberNode.class));
        assertThat(left.eval(), is(new BigDecimal("12")));

        BinaryOpNode right = (BinaryOpNode) n.getRightOperand();
        assertThat(right.getOperator(), is(OP_PLUS));
        assertThat(right.getLeftOperand(), instanceOf(NumberNode.class));
        assertThat(right.getRightOperand(), instanceOf(NumberNode.class));
        assertThat(right.eval(), is(new BigDecimal("8")));
    }

    @Test
    public void parseEqualCondition() {
        Tokenizer tokenizer = new Tokenizer("1 == 2");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parseConditionalExpression(tokenizer);
        assertThat(n.getOperator(), is(OP_EQUAL));
        assertThat(n.getLeftOperand(), instanceOf(NumberNode.class));
        assertThat(n.getRightOperand(), instanceOf(NumberNode.class));
        assertThat(n.eval(), is(false));
    }

    @Test
    public void parseNotEqualCondition() {
        Tokenizer tokenizer = new Tokenizer("1 != 2");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parseConditionalExpression(tokenizer);
        assertThat(n.getOperator(), is(OP_NOT_EQUAL));
        assertThat(n.getLeftOperand(), instanceOf(NumberNode.class));
        assertThat(n.getRightOperand(), instanceOf(NumberNode.class));
        assertThat(n.eval(), is(true));
    }

    @Test
    public void parseLessCondition() {
        Tokenizer tokenizer = new Tokenizer("1 < 1");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parseConditionalExpression(tokenizer);
        assertThat(n.getOperator(), is(OP_LESS));
        assertThat(n.getLeftOperand(), instanceOf(NumberNode.class));
        assertThat(n.getRightOperand(), instanceOf(NumberNode.class));
        assertThat(n.eval(), is(false));
    }

    @Test
    public void parseLessEqualCondition() {
        Tokenizer tokenizer = new Tokenizer("1 <= 1");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parseConditionalExpression(tokenizer);
        assertThat(n.getOperator(), is(OP_LESS_EQUAL));
        assertThat(n.getLeftOperand(), instanceOf(NumberNode.class));
        assertThat(n.getRightOperand(), instanceOf(NumberNode.class));
        assertThat(n.eval(), is(true));
    }

    @Test
    public void parseGreaterCondition() {
        Tokenizer tokenizer = new Tokenizer("1 > 1");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parseConditionalExpression(tokenizer);
        assertThat(n.getOperator(), is(OP_GREATER));
        assertThat(n.getLeftOperand(), instanceOf(NumberNode.class));
        assertThat(n.getRightOperand(), instanceOf(NumberNode.class));
        assertThat(n.eval(), is(false));
    }

    @Test
    public void parseGreaterEqualCondition() {
        Tokenizer tokenizer = new Tokenizer("1 >= 1");
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parseConditionalExpression(tokenizer);
        assertThat(n.getOperator(), is(OP_GREATER_EQUAL));
        assertThat(n.getLeftOperand(), instanceOf(NumberNode.class));
        assertThat(n.getRightOperand(), instanceOf(NumberNode.class));
        assertThat(n.eval(), is(true));
    }
}
