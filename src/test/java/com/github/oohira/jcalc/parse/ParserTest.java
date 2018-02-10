package com.github.oohira.jcalc.parse;

import com.github.oohira.jcalc.token.Token;
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
        Tokenizer tokenizer = new Tokenizer(new Token(NUMBER, "1"));
        Parser parser = new Parser(tokenizer);

        NumberNode n = (NumberNode) parser.parse();
        assertThat(n.eval(), is(new BigDecimal("1")));
    }

    @Test
    public void parseString() {
        Tokenizer tokenizer = new Tokenizer(new Token(STRING, "Hello"));
        Parser parser = new Parser(tokenizer);

        StringNode n = (StringNode) parser.parse();
        assertThat(n.eval(), is("Hello"));
    }

    @Test
    public void parse2Factors() {
        Tokenizer tokenizer = new Tokenizer(
                new Token(NUMBER, "1"), new Token(OP_MULTI, "*"), new Token(NUMBER, "2"));
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parse();
        assertThat(n.getLeftOperand(), instanceOf(NumberNode.class));
        assertThat(n.getLeftOperand().eval(), is(new BigDecimal("1")));
        assertThat(n.getRightOperand(), instanceOf(NumberNode.class));
        assertThat(n.getRightOperand().eval(), is(new BigDecimal("2")));
        assertThat(n.eval(), is(new BigDecimal("2")));
    }

    @Test
    public void parse3Factors() {
        Tokenizer tokenizer = new Tokenizer(new Token(NUMBER, "10"),
                new Token(OP_MULTI, "*"), new Token(NUMBER, "2"),
                new Token(OP_DIV, "/"), new Token(NUMBER, "5"));
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
    public void parse4Factors() {
        Tokenizer tokenizer = new Tokenizer(new Token(NUMBER, "10"),
                new Token(OP_MULTI, "*"), new Token(NUMBER, "2"),
                new Token(OP_DIV, "/"), new Token(NUMBER, "5"),
                new Token(OP_MULTI, "*"), new Token(NUMBER, "3"));
        Parser parser = new Parser(tokenizer);

        BinaryOpNode n = (BinaryOpNode) parser.parse();
        assertThat(n.getOperator(), is(OP_MULTI));
        assertThat(n.getLeftOperand(), instanceOf(BinaryOpNode.class));
        assertThat(n.getRightOperand(), instanceOf(NumberNode.class));
        assertThat(n.eval(), is(new BigDecimal("12")));

        BinaryOpNode left = (BinaryOpNode) n.getLeftOperand();
        assertThat(left.getOperator(), is(OP_DIV));
        assertThat(left.getLeftOperand(), instanceOf(BinaryOpNode.class));
        assertThat(left.getRightOperand(), instanceOf(NumberNode.class));
        assertThat(left.eval(), is(new BigDecimal("4")));

        BinaryOpNode left2 = (BinaryOpNode) left.getLeftOperand();
        assertThat(left2.getOperator(), is(OP_MULTI));
        assertThat(left2.getLeftOperand(), instanceOf(NumberNode.class));
        assertThat(left2.getRightOperand(), instanceOf(NumberNode.class));
        assertThat(left2.eval(), is(new BigDecimal("20")));
    }
}
